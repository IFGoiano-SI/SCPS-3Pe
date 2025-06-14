package Views;

import DAO.EnderecoDAO;
import DAO.ClienteDAO;
import Entidades.Cliente;
import Entidades.Endereco;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.List;

/**
 * Classe que representa a interface gráfica para gerenciar clientes.
 * Permite criar, atualizar, excluir e listar clientes com seus respectivos endereços.
 */
public class ClienteView extends JFrame {
    private ClienteDAO clienteDAO;
    private EnderecoDAO enderecoDAO;


    // Componentes da interface
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private JTextField txtNome, txtEmail;
    private JFormattedTextField txtTelefone; // Campo com máscara
    private JTextField txtRua, txtNumero, txtBairro, txtCidade, txtEstado;
    private JFormattedTextField txtCep; // Campo com máscara
    private JButton btnNovo, btnSalvar, btnAtualizar, btnExcluir, btnLimpar, btnVoltar;

    private Cliente clienteSelecionado;

    // Construtor da classe
    public ClienteView() {
        this.clienteDAO = new ClienteDAO();
        this.enderecoDAO = new EnderecoDAO();

        initializeComponents();
        carregarCliente();
    }

    // Método para inicializar os componentes da interface
    private void initializeComponents() {
        setTitle("Gerenciar Clientes");
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

    // Painel do formulário
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
        panel.setPreferredSize(new Dimension(400, 600));

        // Dados pessoais
        JPanel dadosPessoais = new JPanel(new GridBagLayout());
        dadosPessoais.setBorder(BorderFactory.createTitledBorder("Informações do Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        dadosPessoais.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        dadosPessoais.add(txtNome, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 2;
        dadosPessoais.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        try {
            txtTelefone = new JFormattedTextField(mascara("(##) #####-####"));
            txtTelefone.setColumns(20);
        } catch (Exception e) {
            txtTelefone = new JFormattedTextField();
            txtTelefone.setColumns(20);
        }
        dadosPessoais.add(txtTelefone, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 3;
        dadosPessoais.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        dadosPessoais.add(txtEmail, gbc);

        // Dados do endereço
        JPanel enderecoPanel = new JPanel(new GridBagLayout());
        enderecoPanel.setBorder(BorderFactory.createTitledBorder("Endereço"));

        // Rua
        gbc.gridx = 0; gbc.gridy = 0;
        enderecoPanel.add(new JLabel("Rua:"), gbc);
        gbc.gridx = 1;
        txtRua = new JTextField(20);
        enderecoPanel.add(txtRua, gbc);

        // Número
        gbc.gridx = 0; gbc.gridy = 1;
        enderecoPanel.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        txtNumero = new JTextField(20);
        enderecoPanel.add(txtNumero, gbc);

        // Bairro
        gbc.gridx = 0; gbc.gridy = 2;
        enderecoPanel.add(new JLabel("Bairro:"), gbc);
        gbc.gridx = 1;
        txtBairro = new JTextField(20);
        enderecoPanel.add(txtBairro, gbc);

        // Cidade
        gbc.gridx = 0; gbc.gridy = 3;
        enderecoPanel.add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1;
        txtCidade = new JTextField(20);
        enderecoPanel.add(txtCidade, gbc);

        // Estado
        gbc.gridx = 0; gbc.gridy = 4;
        enderecoPanel.add(new JLabel("Estado (UF):"), gbc);
        gbc.gridx = 1;
        txtEstado = new JTextField(20);
        enderecoPanel.add(txtEstado, gbc);

        // CEP
        gbc.gridx = 0; gbc.gridy = 5;
        enderecoPanel.add(new JLabel("CEP:"), gbc);
        gbc.gridx = 1;
        try {
            txtCep = new JFormattedTextField(mascara("#####-###"));
            txtCep.setColumns(20);
        } catch (Exception e) {
            txtCep = new JFormattedTextField();
            txtCep.setColumns(20);
        }
        enderecoPanel.add(txtCep, gbc);

        panel.add(dadosPessoais);
        panel.add(Box.createVerticalStrut(10));
        panel.add(enderecoPanel);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    // Painel da tabela de clientes
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));

        // Criar modelo da tabela
        String[] colunas = {"ID", "Nome", "Endereço", "Telefone", "Email"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Não permite edição direta na tabela
            }
        };

        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Listener para seleção na tabela
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecionarCliente();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Painel de botões
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

    // Configurar ações dos botões
    private void configurarAcoes() {
        btnNovo.addActionListener(e -> novoCliente());
        btnSalvar.addActionListener(e -> salvarCliente());
        btnAtualizar.addActionListener(e -> atualizarCliente());
        btnExcluir.addActionListener(e -> excluircliente());
        btnLimpar.addActionListener(e -> limparCampos());
    }

    // Método para criar um novo cliente
    private void novoCliente() {
        limparCampos();
        clienteSelecionado = null;
        btnSalvar.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnExcluir.setEnabled(false);
        txtNome.requestFocus();
    }

    // Método para salvar cliente (lógica de inserção)
    private void salvarCliente() {
        if (!validarCampos()) return;

        try {
            // Criar endereço
            Endereco endereco = new Endereco();
            endereco.setRua(txtRua.getText().trim());
            endereco.setNumero(txtNumero.getText().trim());
            endereco.setBairro(txtBairro.getText().trim());
            endereco.setCidade(txtCidade.getText().trim());
            endereco.setEstado(txtEstado.getText().trim().toUpperCase());
            endereco.setCep(txtCep.getText().trim());

            // Inserir endereço no banco
            int idEndereco = enderecoDAO.inserir(endereco);
            if (idEndereco <= 0) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar endereço!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            endereco.setId(idEndereco);

            // Criar cliente
            Cliente cliente = new Cliente();
            cliente.setNome(txtNome.getText().trim());
            cliente.setTelefone(txtTelefone.getText().trim());
            cliente.setEmail(txtEmail.getText().trim());
            cliente.setEndereco(endereco);

            // Inserir cliente no banco
            int idCliente = clienteDAO.inserir(cliente);
            if (idCliente > 0) {
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarCliente();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para atualizar cliente (lógica de atualização)
    private void atualizarCliente() {
        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validarCampos()) return;

        try {
            // Atualizar dados do cliente
            clienteSelecionado.setNome(txtNome.getText().trim());
            clienteSelecionado.setTelefone(txtTelefone.getText().trim());
            clienteSelecionado.setEmail(txtEmail.getText().trim());

            // Atualizar endereço
            Endereco endereco = clienteSelecionado.getEndereco();
            endereco.setRua(txtRua.getText().trim());
            endereco.setNumero(txtNumero.getText().trim());
            endereco.setBairro(txtBairro.getText().trim());
            endereco.setCidade(txtCidade.getText().trim());
            endereco.setEstado(txtEstado.getText().trim().toUpperCase());
            endereco.setCep(txtCep.getText().trim());
            // Salvar no banco
            if (enderecoDAO.atualizar(endereco) && clienteDAO.atualizar(clienteSelecionado)) {
                JOptionPane.showMessageDialog(this, "Clienet atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarCliente();
                limparCampos();
                clienteSelecionado = null;
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para excluir cliente (lógica de exclusão)
    private void excluircliente() {
        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o cliente " + clienteSelecionado.getNome() + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                // Exclusão lógica - marcar como inativo
                clienteSelecionado.setAtivo(0);

                if (clienteDAO.atualizar(clienteSelecionado)) {
                    JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarCliente();
                    limparCampos();
                    clienteSelecionado = null;
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para selecionar um cliente da tabela
    private void selecionarCliente() {
        int linha = tabelaClientes.getSelectedRow();
        if (linha >= 0) {
            int id = (Integer) modeloTabela.getValueAt(linha, 0);
            clienteSelecionado = clienteDAO.buscarPorId(id);

            if (clienteSelecionado != null) {
                preencherCampos(clienteSelecionado);
                btnSalvar.setEnabled(false);
                btnAtualizar.setEnabled(true);
                btnExcluir.setEnabled(true);
            }
        }
    }

    // Método para preencher os campos do formulário com os dados do cliente selecionado
    private void preencherCampos(Cliente cliente) {
        txtNome.setText(cliente.getNome());
        txtTelefone.setText(cliente.getTelefone());
        txtEmail.setText(cliente.getEmail());

        // Preencher endereço
        Endereco endereco = cliente.getEndereco();
        if (endereco != null) {
            txtRua.setText(endereco.getRua());
            txtNumero.setText(endereco.getNumero());
            txtBairro.setText(endereco.getBairro());
            txtCidade.setText(endereco.getCidade());
            txtEstado.setText(endereco.getEstado());
            txtCep.setText(endereco.getCep());
        }
    }

    // Método para limpar os campos do formulário
    private void limparCampos() {
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtRua.setText("");
        txtNumero.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        txtEstado.setText("");
        txtCep.setText("");

        btnSalvar.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnExcluir.setEnabled(false);

        tabelaClientes.clearSelection();
        clienteSelecionado = null;
    }

    // Método para validar campos obrigatórios
    private boolean validarCampos() {

        // Nome
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtNome.requestFocus();
            return false;
        }

        // Telefone
        if (txtTelefone.getText().trim().isEmpty() || txtTelefone.getText().trim().equals("(  )      -    ")) {
            JOptionPane.showMessageDialog(this, "Telefone é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtTelefone.requestFocus();
            return false;
        }

        // E-mail
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "E-mail é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }

        // Rua
        if (txtRua.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Rua é obrigatória!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtRua.requestFocus();
            return false;
        }

        // Número
        if (txtNumero.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Número é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtNumero.requestFocus();
            return false;
        }

        // Bairro
        if (txtBairro.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bairro é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtBairro.requestFocus();
            return false;
        }

        // Cidade
        if (txtCidade.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cidade é obrigatória!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtCidade.requestFocus();
            return false;
        }

        // Estado
        if (txtEstado.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Estado é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtEstado.requestFocus();
            return false;
        }

        // CEP
        if (txtCep.getText().trim().isEmpty() || txtCep.getText().trim().equals("     -   ")) {
            JOptionPane.showMessageDialog(this, "CEP é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtCep.requestFocus();
            return false;
        }

        return true;
    }

    // Método para carregar a lista de clientes na tabela
    private void carregarCliente() {
        modeloTabela.setRowCount(0);
        try {
            List<Cliente> clientes = clienteDAO.listarTodos();
            for (Cliente cliente : clientes) {
                // Formatação do endereço: "Cidade - UF"
                String enderecoFormatado = "";
                if (cliente.getEndereco() != null) {
                    String cidade = cliente.getEndereco().getCidade();
                    String estado = cliente.getEndereco().getEstado();
                    if (cidade != null && !cidade.trim().isEmpty() &&
                            estado != null && !estado.trim().isEmpty()) {
                        enderecoFormatado = cidade + " - " + estado.toUpperCase();
                    }
                }

                Object[] linha = {
                        cliente.getIdCliente(),
                        cliente.getNome(),
                        enderecoFormatado,
                        cliente.getTelefone(),
                        cliente.getEmail()
                };
                modeloTabela.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar Clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método principal para executar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new Views.ClienteView().setVisible(true);
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
