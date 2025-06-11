package Views;

import Manager.UsuarioManager;
import Entidades.Usuario;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LoginView {
    private static UsuarioManager usuarioManager = new UsuarioManager();
    
    public static void main(String[] args) {
        /**São usados 3 métodos Para definir o visual do Swing de acordo com o sistema operacional:

         UIManager.setLookAndFeel: modifica o visual do Swing.
         UIManager.getSystemLookAndFeelClassName: retorna o visual do sistema operacional em questão (o nome da classe que representa esse visual).
         SwingUtilities.updateComponentTreeUI: aplica o visual em todas as UI que forem abertas a partir daquela.
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(new JFrame());
        } catch (UnsupportedLookAndFeelException e) {
            // Se o look and feel não for suportado, usa o padrão
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }
    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sistema de Controle de Prestação de Serviços - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("SISTEMA DE CONTROLE DE PRESTAÇÃO DE SERVIÇOS", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 102, 204));
        headerPanel.add(titleLabel);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Login title
        JLabel loginLabel = new JLabel("LOGIN", JLabel.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(loginLabel, gbc);
        
        // Reset gridwidth
        gbc.gridwidth = 1;
        
        // Usuario field
        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(userLabel, gbc);
        
        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(userField, gbc);
        
        // Password field
        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);
        
        // Login button
        JButton loginButton = new JButton("Entrar");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(loginButton, gbc);
        
        // Action listener for login button
        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "Por favor, preencha todos os campos.", 
                    "Campos Obrigatórios", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Realizar autenticação
            Usuario usuario = usuarioManager.buscarPorNomeESenha(username, password);
            if (usuario != null) {
                JOptionPane.showMessageDialog(frame, 
                    "Login realizado com sucesso!\nBem-vindo, " + usuario.getNomeUsuario() + "!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Fechar tela de login e abrir menu
                frame.dispose();
                SwingUtilities.invokeLater(() -> {
                    MenuView menuView = new MenuView(usuario);
                    menuView.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "Nome de usuário ou senha inválidos.\nTente novamente.", 
                    "Erro de Autenticação", 
                    JOptionPane.ERROR_MESSAGE);
                // Limpar campos
                userField.setText("");
                passwordField.setText("");
                userField.requestFocus();
            }
        });
        
        // Enter key support for password field
        passwordField.addActionListener(e -> loginButton.doClick());
        
        // Footer Panel
        JPanel footerPanel = new JPanel();
        JLabel footerLabel = new JLabel("© 2025 Sistema SCPS", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        frame.setVisible(true);
        
        // Focus on username field
        userField.requestFocus();
    }
}
