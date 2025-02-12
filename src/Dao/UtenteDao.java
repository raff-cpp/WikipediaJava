package Dao;

import model.Utente;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDao {
    private Connection conn;

    public UtenteDao(Connection conn) {
        this.conn = conn;
    }

    public int getIdAutoreByModifica(int idModifica) throws SQLException {
        String query = "SELECT p.id_autore " +
                "FROM MODIFICA m " +
                "JOIN FRASE f ON m.id_frase = f.id_frase " +
                "JOIN TESTO t ON f.id_testo = t.id_testo " +
                "JOIN PAGINA p ON t.id_pagina = p.id_pagina " +
                "WHERE m.id_modifica = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idModifica);
            try (ResultSet rst = stmt.executeQuery()) {
                if (rst.next()) {
                    return rst.getInt("id_autore");
                }
            }
        }
        return -1;
    }






    public int creaUtente(String funzione, String username, String password) throws SQLException {
        String query = "INSERT INTO Utente (username, pass_word, funzione) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, funzione);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return -1;
    }

    public Utente getUtenteByUsername(String username) throws SQLException {
        String sql = "SELECT id_utente, username, pass_word, funzione FROM Utente WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_utente");
                String password = rs.getString("pass_word");
                String funzione = rs.getString("funzione"); // Recupera il ruolo
                return new Utente(id, username, password, funzione);
            }
        }
        return null;
    }

    public String getFunzioneByUsername(String username) throws SQLException {
        String query = "SELECT funzione FROM Utente WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rst = stmt.executeQuery()) {
                if (rst.next()) {
                    return rst.getString("funzione");
                }
            }
        }
        return null;
    }


}