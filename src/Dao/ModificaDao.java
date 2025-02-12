package Dao;

import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModificaDao {
    private Connection conn;

    public ModificaDao(Connection conn) {
        this.conn = conn;
    }




    public List<Modifica> getModificheProposte() throws SQLException {
        List<Modifica> modifiche = new ArrayList<>();
        String sql = "SELECT id_modifica, vecchio_testo, nuovo_testo, stato, id_utente, id_frase FROM modifica WHERE stato = ? ORDER BY data_modifica DESC";


        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, StatoModifica.PROPOSTA.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Modifica modifica = new Modifica(
                        rs.getInt("id_modifica"),
                        rs.getString("nuovo_testo"),
                        rs.getString("vecchio_testo"),
                        StatoModifica.valueOf(rs.getString("stato")),
                        rs.getInt("id_utente"), // Passa anche l'ID dell'utente
                        rs.getInt("id_frase")
                );
                modifiche.add(modifica);
            }
        }
        return modifiche;
    }




    public boolean rifiutaModifica(int idModifica) {
        String query = "UPDATE modifica SET stato = 'RIFIUTATA' WHERE id_modifica = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idModifica);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean accettaModifica(int idModifica) throws SQLException {
        String sql = "UPDATE modifica SET stato = 'ACCETTATA' WHERE id_modifica = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idModifica);
            stmt.executeUpdate();
        }
        return false;
    }
    public boolean salvaModifica(Modifica modifica, int idFrase) throws SQLException {
        String sql = "INSERT INTO MODIFICA (ora_modifica, data_modifica, nuovo_testo, vecchio_testo, stato, id_utente, id_frase) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String query = "SELECT setval('modifica_id_seq', (SELECT MAX(id_modifica) FROM modifica)+1);";


        try (PreparedStatement stmt2 = conn.prepareStatement(query)) {
            stmt2.executeQuery();
        }

        // Inserisci la modifica nel database
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTime(1, java.sql.Time.valueOf(modifica.getOraModifica())); // Ora della modifica
            stmt.setDate(2, java.sql.Date.valueOf(modifica.getDataModifica())); // Data della modifica
            stmt.setString(3, modifica.getNuovoTesto()); // Nuovo testo
            stmt.setString(4, modifica.getVecchioTesto()); // Vecchio testo
            stmt.setString(5, modifica.getStato().name()); // Stato della modifica
            stmt.setInt(6, modifica.getProponente().getIdUtente()); // ID utente proponente
            stmt.setInt(7, idFrase); // ID frase a cui si riferisce la modifica

            return stmt.executeUpdate() > 0;
        }
    }

}
