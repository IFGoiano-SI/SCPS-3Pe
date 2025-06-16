package Views;

// import Manager.ClienteManager;
// import Manager.FuncionarioManager;
// import Manager.OrdemServicoManager;
// import Manager.UsuarioManager;
import Entidades.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {
    private Usuario usuarioLogado;
    // private ClienteManager clienteManager;
    // private FuncionarioManager funcionarioManager;
    // private OrdemServicoManager ordemServicoManager;
    // private UsuarioManager usuarioManager;

    public MenuView(Usuario usuario) {
        this.usuarioLogado = usuario;
        // this.clienteManager = new ClienteManager();
        // this.funcionarioManager = new FuncionarioManager();
        // this.ordemServicoManager = new OrdemServicoManager();
        // this.usuarioManager = new UsuarioManager();

        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("Sistema de Controle de Prestação de Serviços - Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel(
                "Bem-vindo, " + usuarioLogado.getNomeUsuario() + " (" + usuarioLogado.getTipoUsuario() + ")");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(welcomeLabel);

        // Main Panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("MENU PRINCIPAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Reset gridwidth for buttons
        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // Cadastros Button
        JButton cadastrosButton = new JButton("Cadastros");
        cadastrosButton.setPreferredSize(new Dimension(200, 40));
        cadastrosButton.addActionListener(e -> abrirMenuCadastros());
        gbc.gridx = 0;
        mainPanel.add(cadastrosButton, gbc);

        // Ordem de Serviços Button
        JButton ordensButton = new JButton("Ordem de Serviços");
        ordensButton.setPreferredSize(new Dimension(200, 40));
        ordensButton.addActionListener(e -> abrirMenuOrdemServicos());
        gbc.gridx = 1;
        mainPanel.add(ordensButton, gbc);        // Relatórios Button
        JButton relatoriosButton = new JButton("Relatórios");
        relatoriosButton.setPreferredSize(new Dimension(200, 40));
        relatoriosButton.addActionListener(e -> abrirRelatorios());
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(relatoriosButton, gbc);

        // Configurações Button (placeholder)
        JButton configButton = new JButton("Configurações");
        configButton.setPreferredSize(new Dimension(200, 40));
        configButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento"));
        gbc.gridx = 1;
        mainPanel.add(configButton, gbc);

        // Footer Panel with Logout button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Sair");
        logoutButton.addActionListener(e -> logout());
        footerPanel.add(logoutButton);

        // Add panels to frame
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void abrirMenuCadastros() {
        String[] opcoes = { "Funcionário", "Cliente", "Usuário" };
        String escolha = (String) JOptionPane.showInputDialog(
                this,
                "Escolha o tipo de cadastro:",
                "Menu de Cadastros",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (escolha != null) {
            switch (escolha) {
                case "Funcionário":
                    SwingUtilities.invokeLater(() -> {
                        FuncionarioView funcionarioView = new FuncionarioView();
                        funcionarioView.setVisible(true);
                    });
                    break;
                case "Cliente":
                    SwingUtilities.invokeLater(() -> {
                        ClienteView clienteView = new ClienteView();
                        clienteView.setVisible(true);
                    });
                    break;
                case "Usuário":
                    JOptionPane.showMessageDialog(this, "Funcionalidade de cadastro de usuário em desenvolvimento");
                    break;
            }
        }
    }

    private void abrirMenuOrdemServicos() {
        SwingUtilities.invokeLater(() -> {
            OrdemServicoView ordemServicoView = new OrdemServicoView();
            ordemServicoView.setVisible(true);
        });
    }

    private void abrirRelatorios() {
        SwingUtilities.invokeLater(() -> {
            RelatorioView relatorioView = new RelatorioView();
            relatorioView.setVisible(true);
        });
    }

    private void logout() {
        String[] opcoes = { "Sim", "Não" };
        int confirmacao = JOptionPane.showOptionDialog(
                this,
                "Deseja realmente sair do sistema?",
                "Confirmar Saída",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[1] // "Não" como botão padrão
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            this.dispose();
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof JFrame && window.isDisplayable()) {
                    window.dispose();
                }
            }

            // Reabrir a tela de login
            SwingUtilities.invokeLater(() -> {
                LoginView.main(new String[0]);
            });
        }
    }

}
