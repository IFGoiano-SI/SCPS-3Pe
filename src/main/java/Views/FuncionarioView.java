package Views;

import Manager.FuncionarioManager;
import Manager.UsuarioManager;
import Entidades.Funcionario;
import Entidades.Endereco;
import Entidades.Usuario;
import DAO.FuncionarioDAO;
import DAO.EnderecoDAO;
import DAO.UsuarioDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

public class FuncionarioView extends JFrame {
    private FuncionarioDAO funcionarioDAO;
    private EnderecoDAO enderecoDAO;
    private UsuarioDAO usuarioDAO;
    
    // Componentes da interface
    private JTable tabelaFuncionarios;
    private DefaultTableModel modeloTabela;
    private JTextField txtNome, txtCargo, txtTelefone, txtEmail;
    private JTextField txtRua, txtNumero, txtBairro, txtCidade, txtEstado, txtCep;
    private JTextField txtNomeUsuario, txtSenha;
    private JComboBox<String> cbTipoUsuario;
    private JButton btnNovo, btnSalvar, btnAtualizar, btnExcluir, btnLimpar, btnVoltar;
    
    private Funcionario funcionarioSelecionado;
    
    public FuncionarioView() {
        this.funcionarioDAO = new FuncionarioDAO();
        this.enderecoDAO = new EnderecoDAO();
        this.usuarioDAO = new UsuarioDAO();
        
        initializeComponents();
        carregarFuncionarios();
    }
    
    private void initializeComponents() {
        setTitle("Gerenciar Funcionários");
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
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Funcionário"));
        panel.setPreferredSize(new Dimension(400, 600));
        
        // Dados pessoais
        JPanel dadosPessoais = new JPanel(new GridBagLayout());
        dadosPessoais.setBorder(BorderFactory.createTitledBorder("Informações Pessoais"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        dadosPessoais.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        dadosPessoais.add(txtNome, gbc);
        
        // Cargo
        gbc.gridx = 0; gbc.gridy = 1;
        dadosPessoais.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1;
        txtCargo = new JTextField(20);
        dadosPessoais.add(txtCargo, gbc);
        
        // Telefone
        gbc.gridx = 0; gbc.gridy = 2;
        dadosPessoais.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(20);
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
        enderecoPanel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        txtEstado = new JTextField(20);
        enderecoPanel.add(txtEstado, gbc);
        
        // CEP
        gbc.gridx = 0; gbc.gridy = 5;
        enderecoPanel.add(new JLabel("CEP:"), gbc);
        gbc.gridx = 1;
        txtCep = new JTextField(20);
        enderecoPanel.add(txtCep, gbc);
        
        // Dados do usuário
        JPanel usuarioPanel = new JPanel(new GridBagLayout());
        usuarioPanel.setBorder(BorderFactory.createTitledBorder("Dados de Usuário"));
        
        // Nome de usuário
        gbc.gridx = 0; gbc.gridy = 0;
        usuarioPanel.add(new JLabel("Nome Usuário:"), gbc);
        gbc.gridx = 1;
        txtNomeUsuario = new JTextField(20);
        usuarioPanel.add(txtNomeUsuario, gbc);
        
        // Senha
        gbc.gridx = 0; gbc.gridy = 1;
        usuarioPanel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        txtSenha = new JPasswordField(20);
        usuarioPanel.add(txtSenha, gbc);
        
        // Tipo usuário
        gbc.gridx = 0; gbc.gridy = 2;
        usuarioPanel.add(new JLabel("Tipo Usuário:"), gbc);
        gbc.gridx = 1;
        cbTipoUsuario = new JComboBox<>(new String[]{"FUNCIONARIO", "ADMIN"});
        cbTipoUsuario.setSelectedItem("FUNCIONARIO");
        usuarioPanel.add(cbTipoUsuario, gbc);
        
        panel.add(dadosPessoais);
        panel.add(Box.createVerticalStrut(10));
        panel.add(enderecoPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(usuarioPanel);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Funcionários"));
        
        // Criar modelo da tabela
        String[] colunas = {"ID", "Nome", "Cargo", "Telefone", "Email", "Usuário"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Não permite edição direta na tabela
            }
        };
        
        tabelaFuncionarios = new JTable(modeloTabela);
        tabelaFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Listener para seleção na tabela
        tabelaFuncionarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecionarFuncionario();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabelaFuncionarios);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
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
    
    private void configurarAcoes() {
        btnNovo.addActionListener(e -> novoFuncionario());
        btnSalvar.addActionListener(e -> salvarFuncionario());
        btnAtualizar.addActionListener(e -> atualizarFuncionario());
        btnExcluir.addActionListener(e -> excluirFuncionario());
        btnLimpar.addActionListener(e -> limparCampos());
    }
    
    private void novoFuncionario() {
        limparCampos();
        funcionarioSelecionado = null;
        btnSalvar.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnExcluir.setEnabled(false);
        txtNome.requestFocus();
    }
    
    private void salvarFuncionario() {
        if (!validarCampos()) return;
        
        try {
            // Criar endereço
            Endereco endereco = new Endereco();
            endereco.setRua(txtRua.getText().trim());
            endereco.setNumero(txtNumero.getText().trim());
            endereco.setBairro(txtBairro.getText().trim());
            endereco.setCidade(txtCidade.getText().trim());
            endereco.setEstado(txtEstado.getText().trim());
            endereco.setCep(txtCep.getText().trim());
            
            // Inserir endereço no banco
            int idEndereco = enderecoDAO.inserir(endereco);
            if (idEndereco <= 0) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar endereço!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            endereco.setId(idEndereco);
            
            // Criar usuário
            Usuario usuario = new Usuario();
            usuario.setNomeUsuario(txtNomeUsuario.getText().trim());
            usuario.setSenha(txtSenha.getText().trim());
            usuario.setTipoUsuario((String) cbTipoUsuario.getSelectedItem());
            
            // Inserir usuário no banco
            int idUsuario = usuarioDAO.inserir(usuario);
            if (idUsuario <= 0) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            usuario.setIdUsuario(idUsuario);
            
            // Criar funcionário
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(txtNome.getText().trim());
            funcionario.setCargo(txtCargo.getText().trim());
            funcionario.setTelefone(txtTelefone.getText().trim());
            funcionario.setEmail(txtEmail.getText().trim());
            funcionario.setEndereco(endereco);
            funcionario.setUsuario(usuario);
            
            // Inserir funcionário no banco
            int idFuncionario = funcionarioDAO.inserir(funcionario);
            if (idFuncionario > 0) {
                JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarFuncionarios();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarFuncionario() {
        if (funcionarioSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validarCampos()) return;
        
        try {
            // Atualizar dados do funcionário
            funcionarioSelecionado.setNome(txtNome.getText().trim());
            funcionarioSelecionado.setCargo(txtCargo.getText().trim());
            funcionarioSelecionado.setTelefone(txtTelefone.getText().trim());
            funcionarioSelecionado.setEmail(txtEmail.getText().trim());
            
            // Atualizar endereço
            Endereco endereco = funcionarioSelecionado.getEndereco();
            endereco.setRua(txtRua.getText().trim());
            endereco.setNumero(txtNumero.getText().trim());
            endereco.setBairro(txtBairro.getText().trim());
            endereco.setCidade(txtCidade.getText().trim());
            endereco.setEstado(txtEstado.getText().trim());
            endereco.setCep(txtCep.getText().trim());
            
            // Atualizar usuário (apenas nome de usuário e tipo)
            Usuario usuario = funcionarioSelecionado.getUsuario();
            usuario.setNomeUsuario(txtNomeUsuario.getText().trim());
            usuario.setTipoUsuario((String) cbTipoUsuario.getSelectedItem());
            
            // Atualizar senha apenas se foi preenchida
            if (!txtSenha.getText().trim().isEmpty()) {
                usuario.setSenha(txtSenha.getText().trim());
            }
            
            // Salvar no banco
            if (enderecoDAO.atualizar(endereco) && usuarioDAO.atualizar(usuario) && funcionarioDAO.atualizar(funcionarioSelecionado)) {
                JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarFuncionarios();
                limparCampos();
                funcionarioSelecionado = null;
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar funcionário!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirFuncionario() {
        if (funcionarioSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir o funcionário " + funcionarioSelecionado.getNome() + "?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                // Exclusão lógica - marcar como inativo
                funcionarioSelecionado.setAtivo(0);
                
                if (funcionarioDAO.atualizar(funcionarioSelecionado)) {
                    JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarFuncionarios();
                    limparCampos();
                    funcionarioSelecionado = null;
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir funcionário!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void selecionarFuncionario() {
        int linha = tabelaFuncionarios.getSelectedRow();
        if (linha >= 0) {
            int id = (Integer) modeloTabela.getValueAt(linha, 0);
            funcionarioSelecionado = funcionarioDAO.buscarPorId(id);
            
            if (funcionarioSelecionado != null) {
                preencherCampos(funcionarioSelecionado);
                btnSalvar.setEnabled(false);
                btnAtualizar.setEnabled(true);
                btnExcluir.setEnabled(true);
            }
        }
    }
    
    private void preencherCampos(Funcionario funcionario) {
        txtNome.setText(funcionario.getNome());
        txtCargo.setText(funcionario.getCargo());
        txtTelefone.setText(funcionario.getTelefone());
        txtEmail.setText(funcionario.getEmail());
        
        // Preencher endereço
        Endereco endereco = funcionario.getEndereco();
        if (endereco != null) {
            txtRua.setText(endereco.getRua());
            txtNumero.setText(endereco.getNumero());
            txtBairro.setText(endereco.getBairro());
            txtCidade.setText(endereco.getCidade());
            txtEstado.setText(endereco.getEstado());
            txtCep.setText(endereco.getCep());
        }
        
        // Preencher usuário
        Usuario usuario = funcionario.getUsuario();
        if (usuario != null) {
            txtNomeUsuario.setText(usuario.getNomeUsuario());
            cbTipoUsuario.setSelectedItem(usuario.getTipoUsuario());
            txtSenha.setText(""); // Não mostrar senha por segurança
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtCargo.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtRua.setText("");
        txtNumero.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        txtEstado.setText("");
        txtCep.setText("");
        txtNomeUsuario.setText("");
        txtSenha.setText("");
        cbTipoUsuario.setSelectedItem("FUNCIONARIO");
        
        btnSalvar.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnExcluir.setEnabled(false);
        
        tabelaFuncionarios.clearSelection();
        funcionarioSelecionado = null;
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtNome.requestFocus();
            return false;
        }
        
        if (txtCargo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cargo é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtCargo.requestFocus();
            return false;
        }
        
        if (txtNomeUsuario.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome de usuário é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtNomeUsuario.requestFocus();
            return false;
        }
        
        // Validar senha apenas se for novo funcionário ou se campo foi preenchido
        if (funcionarioSelecionado == null && txtSenha.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Senha é obrigatória para novo funcionário!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtSenha.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void carregarFuncionarios() {
        modeloTabela.setRowCount(0);
        try {
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
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar funcionários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new FuncionarioView().setVisible(true);
        });
    }

    // Classe utilitária para máscaras de campos
    public static MaskFormatter mascara(String mascara) {

        MaskFormatter F_Mascara = new MaskFormatter();
        try {

            F_Mascara.setMask(mascara); //Atribui a mascara
            F_Mascara.setPlaceholderCharacter(' '); //Caracter para preencimento
        } catch (ParseException excecao) {
            System.out.println(excecao.getMessage());
        }
        return F_Mascara;
    }
}
