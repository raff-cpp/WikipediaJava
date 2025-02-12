package model;

import GUI.LoginGUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:5432/Test", "postgres", "G3nn4r0.25")) {
            System.out.println("Connessione al database stabilita con successo.");

            // Avvio dell'interfaccia grafica di login
            javax.swing.SwingUtilities.invokeLater(() -> {
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
            });

        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database: " + e.getMessage());
        }
    }
}
