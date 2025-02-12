package GUI;

import Controller.RegisterController;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class RegisterFrame extends JFrame {
    private RegisterController registerController;


    public RegisterFrame(Connection conn) {
        this.registerController = new RegisterController();

        setTitle("Registrazione");
        setSize(400, 300); // Dimensioni più piccole
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Pannello principale con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240)); // Sfondo grigio chiaro

        // Titolo
        JLabel headerLabel = new JLabel("Registrazione", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 50, 150));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Pannello per i campi di input
        JPanel inputPanel = new JPanel(new GridLayout(5, 1, 10, 10)); // 5 righe, 1 colonna
        inputPanel.setBackground(new Color(240, 240, 240)); // Sfondo grigio chiaro
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margini

        // Campo per l'username
        JLabel labelNewUsername = new JLabel("Username:");
        JTextField tfNewUsername = new JTextField();

        // Campo per la password
        JLabel labelNewPassword = new JLabel("Password:");
        JPasswordField tfNewPassword = new JPasswordField();

        // Checkbox per il ruolo
        JPanel rolesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rolesPanel.setBackground(new Color(240, 240, 240)); // Sfondo grigio chiaro
        JCheckBox checkBoxAuthor = new JCheckBox("Autore");
        JCheckBox checkBoxUser = new JCheckBox("Utente");
        rolesPanel.add(checkBoxAuthor);
        rolesPanel.add(checkBoxUser);

        // Aggiungi i componenti al pannello di input
        inputPanel.add(labelNewUsername);
        inputPanel.add(tfNewUsername);
        inputPanel.add(labelNewPassword);
        inputPanel.add(tfNewPassword);
        inputPanel.add(rolesPanel);

        // Pulsante di registrazione
        JButton registerButton = new JButton("Registrati");
        registerButton.setBackground(new Color(0, 100, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusable(false);
        registerButton.addActionListener(e -> {
            String username = tfNewUsername.getText().trim();
            String password = new String(tfNewPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "I campi non possono essere vuoti", "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                String funzione = "";
                if (checkBoxAuthor.isSelected()) {
                    funzione = "Autore";
                } else if (checkBoxUser.isSelected()) {
                    funzione = "Utente";
                } else {
                    JOptionPane.showMessageDialog(this, "Seleziona un ruolo", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (registerController.registraUtente(funzione, username, password)) {
                    JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Errore durante la registrazione.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }

        });



        // Aggiungi il pulsante al pannello principale
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(registerButton, BorderLayout.SOUTH);

        // Aggiungi il pannello principale alla finestra
        add(mainPanel);
        setVisible(true);
    }
}