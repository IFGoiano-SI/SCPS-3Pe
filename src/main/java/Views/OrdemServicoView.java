package Views;

import Entidades.*;
import DAO.ClienteDAO;
import DAO.OrdemServicoDAO;
import DAO.FuncionarioDAO;
import DAO.UsuarioDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável pela interface gráfica de gerenciamento das Ordens de Serviço(OS).
 * Permite criar, editar, excluir e listar OS's no sistema.
 */
public class OrdemServicoView extends JFrame {
    private OrdemServicoDAO ordemServicoDAO;
    private ClienteDAO clienteDAO;
    private FuncionarioDAO funcionarioDAO;;
    private UsuarioDAO usuarioDAO;

    // Componentes da interface
    private JTable tabelaOrdemServico;
    private DefaultTableModel modeloTabela;
    private JTextField txtDescricao, txtDtCoclusao, txtValorPago;
    private JComboBox<OrdemServico.Status> statusComboBox;
    private JComboBox<Cliente> clienteComboBox;
    private JComboBox<Funcionario> funcionarioComboBox;
    private JButton btnNovo, btnSalvar, btnAtualizar, btnExcluir, btnLimpar, btnVoltar;

    private OrdemServico osSelecionada;

    // Construtor da classe OrdemServicoView
    public OrdemServicoView() {
        this.ordemServicoDAO = new OrdemServicoDAO();
        this.funcionarioDAO = new FuncionarioDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.clienteDAO = new ClienteDAO();

        initializeComponents();
        carregarOS();
    }

    // Método para inicializar os componentes da interface gráfica
    private void initializeComponents() {
        setTitle("Gerenciar Ordens de Servico");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel do formulário
        JPanel formPanel = createFormPanel();

        // Panel da tabela
        JPanel tablePanel = createTablePanel();

        // Panel dos botões
        JPanel buttonPanel = createButtonPanel();

        // Adicionar ao main panel
        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Configurar ações dos botões
        configurarAcoes();
    }

    // Método para criar o painel do formulário de cadastro/edição de OS
    private JPanel createFormPanel() {
        JPanel ordemPanel = new JPanel();
        ordemPanel.setLayout(new BoxLayout(ordemPanel, BoxLayout.Y_AXIS));
        ordemPanel.setPreferredSize(new Dimension(400, 600));

        // Dados da Ordem de Serviço
        JPanel infoOS = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        infoOS.setBorder(BorderFactory.createTitledBorder("Informações sobre a Ordem de Serviço"));
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Descrição
        gbc.gridx = 0; gbc.gridy = 0;
        infoOS.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        txtDescricao = new JTextField(20);
        infoOS.add(txtDescricao, gbc);

        // Status
        gbc.gridx = 1; gbc.gridy = 1;
        infoOS.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(OrdemServico.Status.values());
        gbc.gridx = 1;
        infoOS.add(statusComboBox);

        // Data Conlusao
        gbc.gridx = 0; gbc.gridy = 2;
        infoOS.add(new JLabel("Data de Conclusão:"), gbc);
        gbc.gridx = 1;
        txtDtCoclusao = new JTextField(20);
        infoOS.add(txtDtCoclusao, gbc);

        // Valor Pago
        gbc.gridx = 0; gbc.gridy = 3;
        infoOS.add(new JLabel("Valor Pago:"), gbc);
        gbc.gridx = 1;
        txtValorPago = new JTextField(6);
        infoOS.add(txtValorPago, gbc);

        // Cliente Relacionado
        JPanel clientePanel = new JPanel(new GridBagLayout());
        clientePanel.setBorder(BorderFactory.createTitledBorder("Cliente Relacionado"));
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        clienteComboBox = new JComboBox<>();
        carregarClientes();
        clientePanel.add(clienteComboBox, gbc);

        // Funcionário Relacionado
        JPanel funcionarioPanel = new JPanel(new GridBagLayout());
        funcionarioPanel.setBorder(BorderFactory.createTitledBorder("Funcionario Relacionado"));
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        funcionarioComboBox = new JComboBox<>();
        carregarFuncionarios();
        funcionarioPanel.add(funcionarioComboBox, gbc);

        ordemPanel.add(infoOS);
        ordemPanel.add(Box.createVerticalStrut(10));
        ordemPanel.add(clientePanel);
        ordemPanel.add(Box.createVerticalStrut(10));
        ordemPanel.add(funcionarioPanel);

        return ordemPanel;
    }

    // Método para criar o painel da tabela de Ordens de Serviço
    private JPanel createTablePanel() {
        JPanel OSpanel = new JPanel(new BorderLayout());
        OSpanel.setBorder(BorderFactory.createTitledBorder("Ordens de Serviço"));

        // Criar modelo da tabela
        String[] colunas = {"ID", "Abertura", "Conclusão", "Status", "Cliente", "Funcionario", "Descrição", "Valor Pago"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Não permite edição direta na tabela
            }
        };

        tabelaOrdemServico = new JTable(modeloTabela);
        tabelaOrdemServico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Listener para seleção na tabela
        tabelaOrdemServico.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecionarOrdemServico();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabelaOrdemServico);
        OSpanel.add(scrollPane, BorderLayout.CENTER);

        return OSpanel;
    }

    // Método para criar o painel dos botões de ação
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        btnNovo = new JButton("Novo");
        btnSalvar = new JButton("Salvar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        btnVoltar = new JButton("Voltar para Menu");
        btnVoltar.addActionListener(e -> {
            dispose(); // Fecha a janela atual
        });

        // Estilizar botões
        btnNovo.setBackground(new Color(0, 123, 255));
//        btnNovo.setForeground(Color.WHITE);

        btnSalvar.setBackground(new Color(40, 167, 69));
//        btnSalvar.setForeground(Color.WHITE);

        btnAtualizar.setBackground(new Color(255, 193, 7));
//        btnAtualizar.setForeground(Color.BLACK);

        btnExcluir.setBackground(new Color(220, 53, 69));
//        btnExcluir.setForeground(Color.WHITE);

        btnLimpar.setBackground(new Color(108, 117, 125));
//        btnLimpar.setForeground(Color.WHITE);

        btnVoltar.setBackground(new Color(14, 0, 0));
        panel.add(btnNovo);
        panel.add(btnSalvar);
        panel.add(btnAtualizar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);
        panel.add(btnVoltar);

        return panel;
    }

    // Método para configurar as ações dos botões
    private void configurarAcoes() {
        btnNovo.addActionListener(e -> novaOrdemServico());
        btnSalvar.addActionListener(e -> salvarOrdemServico());
        btnAtualizar.addActionListener(e -> atualizarOrdemServico());
        btnExcluir.addActionListener(e -> excluirOrdemServico());
        btnLimpar.addActionListener(e -> limparCampos());
    }

    // Método para criar uma nova OS
    private void novaOrdemServico() {
        limparCampos();
        osSelecionada = null;
        btnSalvar.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnExcluir.setEnabled(false);
        txtDescricao.requestFocus();
    }
    OrdemServicoDAO dao = new OrdemServicoDAO();
    // Método para salvar ou atualizar uma Ordem de Servico
    private void salvarOrdemServico() {
        if (!validarCampos()) return;

        try {
            // Escolher Cliente já cadastrado
            Cliente clienteEscolhido = (Cliente) clienteComboBox.getSelectedItem();

            // Escolher Funcionario já cadastrado
            Funcionario funcEscolhido = (Funcionario) funcionarioComboBox.getSelectedItem();

            // Data de Conclusão
            LocalDateTime dtConc = LocalDateTime.parse(txtDtCoclusao.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            // Criar Ordem de Serviço
            OrdemServico os = new OrdemServico();
            os.setDescricao(txtDescricao.getText().trim());
                //Status é gerado padrão como "Aberto"
            os.setCliente(clienteEscolhido);
            os.setFuncionario(funcEscolhido);
            os.setDataAbertura(LocalDateTime.now());
            os.setDataConclusao(dtConc);

            // Inserir Ordem de Serviço no banco
            int idOrdemServico = dao.inserir(os);
            if (idOrdemServico > 0) {
                JOptionPane.showMessageDialog(this, "Ordem de Serviço cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarOS();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar Ordem de Serviço!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar Ordem de Serviço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para atualizar os dados de uma Ordem de Serviço selecionada
    private void atualizarOrdemServico() {
        if (osSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma Ordem de Serviço para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validarCampos()) return;

        try {
            // Atualizar dados da Ordem de Serviço
            osSelecionada.setDescricao(txtDescricao.getText().trim());
            osSelecionada.setStatus((OrdemServico.Status) statusComboBox.getSelectedItem());//Sugestão do InteliJ
            //osSelecionada.setCliente(clienteEscolhido);
            //osSelecionada.setFuncionario(funcEscolhido);
            osSelecionada.setDataConclusao(LocalDateTime.parse(txtDtCoclusao.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

            // Salvar no banco
            if (dao.atualizar(osSelecionada)) {
                JOptionPane.showMessageDialog(this, "Ordem de Serviço atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarOS();
                limparCampos();
                osSelecionada = null;
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar Ordem de Serviço!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar Ordem de Serviço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para excluir (inativar) uma Ordem de Serviço selecionada
    private void excluirOrdemServico() {
        if (osSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma Ordem de Serviço para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir a Ordem de Serviço " + osSelecionada.getDescricao() + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                // Exclusão lógica - marcar como inativo
                osSelecionada.setAtivo(0);

                if (dao.atualizar(osSelecionada)) {
                    JOptionPane.showMessageDialog(this, "Ordem de Serviço excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarOS();
                    limparCampos();
                    osSelecionada = null;
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir Ordem de Serviço!", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir Ordem de Serviço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para selecionar uma OS da tabela e preencher os campos do formulário
    private void selecionarOrdemServico() {
        int linha = tabelaOrdemServico.getSelectedRow();
        if (linha >= 0) {
            int id = (Integer) modeloTabela.getValueAt(linha, 0);
            osSelecionada = ordemServicoDAO.buscarPorId(id);

            if (osSelecionada != null) {
                preencherCampos(osSelecionada);
                btnSalvar.setEnabled(false);
                btnAtualizar.setEnabled(true);
                btnExcluir.setEnabled(true);
            }
        }
    }

    // Método para preencher os campos do formulário com os dados do funcionário selecionado
    private void preencherCampos(OrdemServico os) {
        txtDescricao.setText(os.getDescricao());
        statusComboBox.setSelectedItem(os.getStatus());
        txtDtCoclusao.setText(os.getDataConclusao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        txtValorPago.setText(String.valueOf(os.getValorPago()));
    }

    // Método para limpar os campos do formulário
    private void limparCampos() {
        txtDescricao.setText("");
        statusComboBox.setSelectedIndex(0);
        txtDtCoclusao.setText("");
        txtValorPago.setText("");

        btnSalvar.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnExcluir.setEnabled(false);

        tabelaOrdemServico.clearSelection();
        osSelecionada = null;
    }

    // Método para validar os campos do formulário antes de salvar ou atualizar
    private boolean validarCampos() {

        // Descrição
        if (txtDescricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descrição é obrigatória!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtDescricao.requestFocus();
            return false;
        }

        // Status
        if (statusComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Status é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            statusComboBox.requestFocus();
            return false;
        }

        // Data de Conclusão
        if (txtDtCoclusao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data de Conclusão é obrigatória!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtDtCoclusao.requestFocus();
            return false;
        }

        return true;
    }

    // Método para carregar a lista de OS na tabela
    private void carregarOS() {
        modeloTabela.setRowCount(0);
        try {
            List<OrdemServico> ordensServicos = ordemServicoDAO.listarTodos();
            for (OrdemServico ordemservico : ordensServicos) {
                Object[] linha = {
                        ordemservico.getIdOrdem(),
                        ordemservico.getDataAbertura(),
                        ordemservico.getDataConclusao(),
                        ordemservico.getStatus(),
                        ordemservico.getCliente().getNome(),
                        ordemservico.getFuncionario().getNome(),
                        ordemservico.getDescricao(),
                        ordemservico.getValorPago()
                };
                modeloTabela.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar Ordens de Serviço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Método para carregar a lista de clientes
    private void carregarClientes() {
        try {
            List<Cliente> clientes = clienteDAO.listarTodos();
            for (Cliente cliente : clientes) {
                clienteComboBox.addItem(cliente);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para carregar a lista de funcionários
    private void carregarFuncionarios() {
        try {
            List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
            for (Funcionario funcionario : funcionarios) {
                funcionarioComboBox.addItem(funcionario);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar funcionários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método principal para iniciar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new OrdemServicoView().setVisible(true);
        });
    }

    // Classe utilitária para máscaras de campos
    public static MaskFormatter mascara(String mascara) {
        MaskFormatter F_Mascara = null;
        try {
            F_Mascara = new MaskFormatter(mascara);
            F_Mascara.setPlaceholderCharacter('_');
        } catch (ParseException excecao) {
            System.out.println("Erro ao criar máscara: " + excecao.getMessage());
        }
        return F_Mascara;
    }
}