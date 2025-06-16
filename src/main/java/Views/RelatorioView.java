package Views;

import Entidades.*;
import DAO.ClienteDAO;
import DAO.OrdemServicoDAO;
import DAO.FuncionarioDAO;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.opencsv.CSVWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe responsável pela interface gráfica de relatórios do sistema.
 * Permite visualizar e exportar relatórios em formato CSV e PDF.
 */
public class RelatorioView extends JFrame {
    
    private JTable tabelaRelatorio;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> comboTipoRelatorio;
    private JButton btnGerar;
    private JButton btnExportarCSV;
    private JButton btnExportarPDF;
    private JButton btnVoltar;
    
    private OrdemServicoDAO ordemServicoDAO;
    private ClienteDAO clienteDAO;
    private FuncionarioDAO funcionarioDAO;
    
    private List<Object[]> dadosRelatorio;
    private String[] colunas;
    private String tituloRelatorio;

    public RelatorioView() {
        this.ordemServicoDAO = new OrdemServicoDAO();
        this.clienteDAO = new ClienteDAO();
        this.funcionarioDAO = new FuncionarioDAO();
        this.dadosRelatorio = new ArrayList<>();
        
        initializeComponents();
        setupActions();
    }

    /**
     * Inicializa os componentes da interface gráfica.
     * Configura o layout, painéis, botões e tabela.
     */
    private void initializeComponents() {
        setTitle("Sistema de Relatórios - SCPS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Panel superior - controles
        JPanel topPanel = createTopPanel();
        
        // Panel central - tabela
        JPanel centerPanel = createCenterPanel();
        
        // Panel inferior - botões de ação
        JPanel bottomPanel = createBottomPanel();
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Opções de Relatório"));
        
        panel.add(new JLabel("Tipo de Relatório:"));
        
        comboTipoRelatorio = new JComboBox<>(new String[]{
            "Selecione um relatório...",
            "Ordens de Serviço - Todas",
            "Ordens de Serviço - Em Aberto",
            "Ordens de Serviço - Concluídas",  
            "Clientes Cadastrados",
            "Funcionários Ativos",
            "Relatório de Faturamento"
        });
        panel.add(comboTipoRelatorio);
        
        btnGerar = new JButton("Gerar Relatório");
        btnGerar.setBackground(new Color(0, 123, 255));
        panel.add(btnGerar);
        
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Relatório"));
        
        // Inicializar tabela vazia
        modeloTabela = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaRelatorio = new JTable(modeloTabela);
        tabelaRelatorio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaRelatorio.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JScrollPane scrollPane = new JScrollPane(tabelaRelatorio);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnExportarCSV = new JButton("Exportar CSV");
        btnExportarCSV.setBackground(new Color(40, 167, 69));
        btnExportarCSV.setEnabled(false);
        
        btnExportarPDF = new JButton("Exportar PDF");
        btnExportarPDF.setBackground(new Color(220, 53, 69));
        btnExportarPDF.setEnabled(false);
        
        btnVoltar = new JButton("Voltar");
        // btnVoltar.setBackground(new Color(108, 117, 125));
        
        panel.add(btnExportarCSV);
        panel.add(btnExportarPDF);
        panel.add(btnVoltar);
        
        return panel;
    }

    private void setupActions() {
        btnGerar.addActionListener(e -> gerarRelatorio());
        btnExportarCSV.addActionListener(e -> exportarCSV());
        btnExportarPDF.addActionListener(e -> exportarPDF());
        btnVoltar.addActionListener(e -> dispose());
    }

    private void gerarRelatorio() {
        String tipoSelecionado = (String) comboTipoRelatorio.getSelectedItem();
        
        if ("Selecione um relatório...".equals(tipoSelecionado)) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um tipo de relatório!", 
                                        "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            switch (tipoSelecionado) {
                case "Ordens de Serviço - Todas":
                    gerarRelatorioOrdensServico(null);
                    break;
                case "Ordens de Serviço - Em Aberto":
                    gerarRelatorioOrdensServico(OrdemServico.Status.ABERTO);
                    break;                case "Ordens de Serviço - Concluídas":
                    gerarRelatorioOrdensServico(OrdemServico.Status.CONCLUIDA);
                    break;
                case "Clientes Cadastrados":
                    gerarRelatorioClientes();
                    break;
                case "Funcionários Ativos":
                    gerarRelatorioFuncionarios();
                    break;
                case "Relatório de Faturamento":
                    gerarRelatorioFaturamento();
                    break;
            }
            
            btnExportarCSV.setEnabled(true);
            btnExportarPDF.setEnabled(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage(), 
                                        "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarRelatorioOrdensServico(OrdemServico.Status filtroStatus) {
        tituloRelatorio = "Relatório de Ordens de Serviço";
        if (filtroStatus != null) {
            tituloRelatorio += " - " + filtroStatus.toString();
        }
        
        colunas = new String[]{"ID", "Data Abertura", "Data Conclusão", "Status", 
                               "Cliente", "Funcionário", "Descrição", "Valor Pago"};
        
        modeloTabela.setColumnIdentifiers(colunas);
        modeloTabela.setRowCount(0);
        dadosRelatorio.clear();
        
        List<OrdemServico> ordens = ordemServicoDAO.listarTodos();
        
        for (OrdemServico ordem : ordens) {
            if (filtroStatus == null || ordem.getStatus() == filtroStatus) {
                Object[] linha = {
                    ordem.getIdOrdem(),
                    ordem.getDataAbertura().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    ordem.getDataConclusao() != null ? 
                        ordem.getDataConclusao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : 
                        "Não concluída",
                    ordem.getStatus().toString(),
                    ordem.getCliente() != null ? ordem.getCliente().getNome() : "Cliente removido",
                    ordem.getFuncionario() != null ? ordem.getFuncionario().getNome() : "Funcionário removido",
                    ordem.getDescricao(),
                    String.format("R$ %.2f", ordem.getValorPago())
                };
                
                modeloTabela.addRow(linha);
                dadosRelatorio.add(linha);
            }
        }
        
        ajustarColunas();
    }

    private void gerarRelatorioClientes() {
        tituloRelatorio = "Relatório de Clientes Cadastrados";
        colunas = new String[]{"ID", "Nome", "Telefone", "Email", "Endereço"};
        
        modeloTabela.setColumnIdentifiers(colunas);
        modeloTabela.setRowCount(0);
        dadosRelatorio.clear();
        
        List<Cliente> clientes = clienteDAO.listarTodos();
        
        for (Cliente cliente : clientes) {
            String endereco = "";
            if (cliente.getEndereco() != null) {
                Endereco end = cliente.getEndereco();
                endereco = end.getRua() + ", " + end.getNumero() + " - " + 
                          end.getBairro() + ", " + end.getCidade() + "/" + end.getEstado();
            }
            
            Object[] linha = {
                cliente.getIdCliente(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                endereco
            };
            
            modeloTabela.addRow(linha);
            dadosRelatorio.add(linha);
        }
        
        ajustarColunas();
    }

    private void gerarRelatorioFuncionarios() {
        tituloRelatorio = "Relatório de Funcionários Ativos";
        colunas = new String[]{"ID", "Nome", "Cargo", "Telefone", "Email", "Usuário"};
        
        modeloTabela.setColumnIdentifiers(colunas);
        modeloTabela.setRowCount(0);
        dadosRelatorio.clear();
        
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
        
        for (Funcionario funcionario : funcionarios) {
            Object[] linha = {
                funcionario.getIdFuncionario(),
                funcionario.getNome(),
                funcionario.getCargo(),
                funcionario.getTelefone(),
                funcionario.getEmail(),
                funcionario.getUsuario() != null ? funcionario.getUsuario().getNomeUsuario() : "N/A"
            };
            
            modeloTabela.addRow(linha);
            dadosRelatorio.add(linha);
        }
        
        ajustarColunas();
    }

    private void gerarRelatorioFaturamento() {
        tituloRelatorio = "Relatório de Faturamento";
        colunas = new String[]{"Mês/Ano", "Ordens Concluídas", "Valor Total", "Valor Médio"};
        
        modeloTabela.setColumnIdentifiers(colunas);
        modeloTabela.setRowCount(0);
        dadosRelatorio.clear();
        
        List<OrdemServico> ordens = ordemServicoDAO.listarTodos();
        
        // Agrupar por mês/ano
        java.util.Map<String, List<OrdemServico>> ordensAgrupadas = new java.util.HashMap<>();
          for (OrdemServico ordem : ordens) {
            if (ordem.getStatus() == OrdemServico.Status.CONCLUIDA && ordem.getDataConclusao() != null) {
                String chave = ordem.getDataConclusao().format(DateTimeFormatter.ofPattern("MM/yyyy"));
                ordensAgrupadas.computeIfAbsent(chave, k -> new ArrayList<>()).add(ordem);
            }
        }
        
        for (java.util.Map.Entry<String, List<OrdemServico>> entrada : ordensAgrupadas.entrySet()) {
            List<OrdemServico> ordensDoMes = entrada.getValue();
            double valorTotal = ordensDoMes.stream().mapToDouble(OrdemServico::getValorPago).sum();
            double valorMedio = valorTotal / ordensDoMes.size();
              Object[] linha = {
                entrada.getKey(),
                ordensDoMes.size(),
                String.format("R$ %.2f", valorTotal),
                String.format("R$ %.2f", valorMedio)
            };
            
            modeloTabela.addRow(linha);
            dadosRelatorio.add(linha);
        }
        
        ajustarColunas();
    }

    private void ajustarColunas() {
        if (tabelaRelatorio.getColumnModel().getColumnCount() > 0) {
            tabelaRelatorio.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
    }

    private void exportarCSV() {
        if (dadosRelatorio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há dados para exportar!", 
                                        "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório CSV");
        fileChooser.setSelectedFile(new java.io.File(
            tituloRelatorio.replaceAll("[^a-zA-Z0-9]", "_") + "_" + 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                
                // Escrever cabeçalho
                writer.writeNext(colunas);
                
                // Escrever dados
                for (Object[] linha : dadosRelatorio) {
                    String[] linhaString = new String[linha.length];
                    for (int i = 0; i < linha.length; i++) {
                        linhaString[i] = linha[i] != null ? linha[i].toString() : "";
                    }
                    writer.writeNext(linhaString);
                }
                
                JOptionPane.showMessageDialog(this, "Arquivo CSV exportado com sucesso!\n" + 
                                            fileChooser.getSelectedFile().getAbsolutePath(), 
                                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar CSV: " + e.getMessage(), 
                                            "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportarPDF() {
        if (dadosRelatorio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há dados para exportar!", 
                                        "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório PDF");
        fileChooser.setSelectedFile(new java.io.File(
            tituloRelatorio.replaceAll("[^a-zA-Z0-9]", "_") + "_" + 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                Document document = new Document(PageSize.A4.rotate()); // Paisagem para mais espaço
                PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile()));
                document.open();
                  // Título
                com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
                Paragraph titulo = new Paragraph(tituloRelatorio, tituloFont);
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(20);
                document.add(titulo);
                  // Data de geração
                com.itextpdf.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
                Paragraph dataGeracao = new Paragraph("Gerado em: " + 
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), dataFont);
                dataGeracao.setAlignment(Element.ALIGN_RIGHT);
                dataGeracao.setSpacingAfter(20);
                document.add(dataGeracao);
                
                // Tabela
                PdfPTable table = new PdfPTable(colunas.length);
                table.setWidthPercentage(100);
                  // Cabeçalho da tabela
                com.itextpdf.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.WHITE);
                for (String coluna : colunas) {
                    PdfPCell cell = new PdfPCell(new Phrase(coluna, headerFont));
                    cell.setBackgroundColor(BaseColor.DARK_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5);
                    table.addCell(cell);
                }
                  // Dados da tabela
                com.itextpdf.text.Font dataTableFont = FontFactory.getFont(FontFactory.HELVETICA, 7, BaseColor.BLACK);
                for (Object[] linha : dadosRelatorio) {
                    for (Object item : linha) {
                        PdfPCell cell = new PdfPCell(new Phrase(item != null ? item.toString() : "", dataTableFont));
                        cell.setPadding(3);
                        table.addCell(cell);
                    }
                }
                
                document.add(table);
                  // Rodapé
                com.itextpdf.text.Font rodapeFont = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY);
                Paragraph rodape = new Paragraph("\nSistema de Controle de Prestação de Serviços - SCPS\n" +
                    "Total de registros: " + dadosRelatorio.size(), rodapeFont);
                rodape.setAlignment(Element.ALIGN_CENTER);
                rodape.setSpacingBefore(20);
                document.add(rodape);
                
                document.close();
                
                JOptionPane.showMessageDialog(this, "Arquivo PDF exportado com sucesso!\n" + 
                                            fileChooser.getSelectedFile().getAbsolutePath(), 
                                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar PDF: " + e.getMessage(), 
                                            "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RelatorioView().setVisible(true);
        });
    }
}
