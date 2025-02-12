package GUI;

import Controller.LoginController;
import DatabaseConnection.DatabaseConnection;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginGUI extends JFrame {
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JCheckBox ckAutore, ckUtente;
    private JButton loginButton;

    public LoginGUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Wikipedia - Login");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Imposta layout principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Pannello laterale con immagine/logo
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(255, 255, 255));
        JLabel logoLabel = new JLabel(new ImageIcon("C:\\Users\\GENNARO\\Desktop\\wikipedia.png"));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(Box.createVerticalGlue());  // Per centrare il logo
        sidePanel.add(logoLabel);
        sidePanel.add(Box.createVerticalGlue());
        mainPanel.add(sidePanel, BorderLayout.WEST);

        // Pannello centrale
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30)); // Padding

        // Header
        JLabel headerLabel = new JLabel("Benvenuto su Wikipedia", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        headerLabel.setForeground(new Color(0, 50, 150));
        centerPanel.add(headerLabel);
        centerPanel.add(Box.createVerticalStrut(20));

        // Pannello login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(5, 1, 10, 10)); // Layout con 5 righe
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setOpaque(false);

        JLabel labelUsername = new JLabel("Username:");
        labelUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        tfUsername = new JTextField(15);
        tfUsername.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel labelPassword = new JLabel("Password:");
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        tfPassword = new JPasswordField(15);
        tfPassword.setFont(new Font("Arial", Font.PLAIN, 14));

        ckAutore = new JCheckBox("Autore");
        ckAutore.setFont(new Font("Arial", Font.PLAIN, 14));
        ckAutore.addActionListener(e->validateLogin());
        ckUtente = new JCheckBox("Utente");
        ckUtente.setFont(new Font("Arial", Font.PLAIN, 14));
        ckUtente.addActionListener(e->validateLogin());

        // Aggiungi checkbox in un singolo pannello
        JPanel userTypePanel = new JPanel();
        userTypePanel.setBackground(Color.WHITE);
        userTypePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userTypePanel.add(ckAutore);
        userTypePanel.add(ckUtente);

        loginPanel.add(labelUsername);
        loginPanel.add(tfUsername);
        loginPanel.add(labelPassword);
        loginPanel.add(tfPassword);
        loginPanel.add(userTypePanel);

        centerPanel.add(loginPanel);

        // Pannello pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout());

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 50, 150));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this::loginButtonActionPerformed);
        loginButton.setEnabled(false);

        JButton registerButton = new JButton("Registrati");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(200, 200, 255));
        registerButton.setForeground(new Color(0, 50, 150));
        registerButton.setPreferredSize(new Dimension(120, 40));
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> {
            try {
                new RegisterFrame(DatabaseConnection.getInstance().getConnection()).setVisible(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        centerPanel.add(buttonPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Layout per visualizzazione
        pack();
    }

    private void validateLogin() {
        loginButton.setEnabled(!(ckAutore.isSelected() && ckUtente.isSelected()));
    }

    private void loginButtonActionPerformed(ActionEvent e) {
        String username = tfUsername.getText();
        String password = new String(tfPassword.getPassword());

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            LoginController controller = new LoginController(conn);
            Utente utente = controller.autenticaUtente(username, password);

            if (utente != null) {
                String funzione = controller.getFunzioneByUsername(utente.getUsername());
                if ("Autore".equalsIgnoreCase(funzione) && ckAutore.isSelected()) {
                    new AutoreGUI(conn,utente.getIdUtente()).setVisible(true);
                } else if ("Utente".equalsIgnoreCase(funzione) && ckUtente.isSelected()) {
                    new UtenteGUI(conn, utente).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Ruolo non corretto selezionato!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Credenziali errate!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}
