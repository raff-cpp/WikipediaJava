package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReputazioneDao {
    private Connection conn;

    public ReputazioneDao(Connection conn) {
        this.conn = conn;
    }

    public String getReputazioneByIdUtente(int idUtente) throws SQLException {
        String sql = "SELECT * FROM reputazione WHERE id_utente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUtente);
            try (ResultSet rst = stmt.executeQuery()) {
                if (rst.next()) {

                    int modificheAcc = rst.getInt("n_modificheacc");
                    int modificheProp = rst.getInt("n_modificheprop");
                    int numeroPag = rst.getInt("n_pagine");
                    int id = rst.getInt("id_utente");
                    float risultato = ((float) modificheAcc /numeroPag);
                    int risultato2 = (int) (risultato*100);
                    return  id + ": " + risultato2 + "%";

                }
            }
        }
        return null;

    }

    public boolean incrementaModificheAcc(int idUtente) throws SQLException {
        String sql = "UPDATE reputazione " +
                "SET n_modificheacc = n_modificheacc + 1, " +
                "n_pagine = n_pagine + 1 " +
                "WHERE id_utente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUtente);
            return stmt.executeUpdate() > 0; // Restituisce true se almeno una riga è stata aggiornata
        }
    }

    public boolean incrementaModificheProp(int idUtente) throws SQLException {
        String sql = "UPDATE reputazione SET n_modificheprop = n_modificheprop + 1, n_pagine = n_pagine + 1 WHERE id_utente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUtente);
            return stmt.executeUpdate()>0;
        }
    }

    public void resettaReputazioneByIdUtente(int idUtente) throws SQLException {
        String query = "INSERT INTO Reputazione (n_pagine, n_modificheAcc, n_modificheProp, id_utente) VALUES (0, 0, 0, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUtente);
            stmt.executeUpdate();
        }
    }

}