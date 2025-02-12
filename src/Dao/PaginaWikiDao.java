package Dao;

import model.Frase;
import model.PaginaWiki;
import model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaginaWikiDao {
    private Connection conn;

    public PaginaWikiDao(Connection conn) {
        this.conn = conn;
    }

    public List<PaginaWiki> trovaPaginePerAutore(int idAutore) throws SQLException {
        List<PaginaWiki> pagine = new ArrayList<>();
        String query = "SELECT * FROM PAGINA WHERE id_autore = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idAutore);
            try (ResultSet rst = stmt.executeQuery()) {
                while (rst.next()) {
                    pagine.add(new PaginaWiki(
                            rst.getInt("id_pagina"),
                            rst.getString("titolo"),
                            rst.getDate("data_creazione"),
                            rst.getTime("ora_creazione"),
                            getAutoreById(rst.getInt("id_autore")) // Recupera l'autore
                    ));
                }
            }
        }
        return pagine;
    }



    public PaginaWiki getPaginaByTitolo(String titolo) throws SQLException {
        String sql = "SELECT * FROM pagina WHERE titolo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titolo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PaginaWiki(
                        rs.getInt("id_pagina"),
                        rs.getString("titolo"),
                        rs.getDate("data_creazione"),
                        rs.getTime("ora_creazione"),
                        getAutoreById(rs.getInt("id_autore")) // Recupera l'autore
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Frase getFrasiByPaginaId(int idPagina) throws SQLException {
        Frase frasi = null;
        String sql = "SELECT * FROM PAGINA JOIN TESTO ON PAGINA.id_pagina = TESTO.id_pagina JOIN FRASE ON FRASE.id_testo = TESTO.id_testo WHERE TESTO.id_pagina = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPagina);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                frasi = new Frase(
                        rs.getInt("id_frase"),
                        rs.getString("testo_frase")
                );

            }
        }
        return frasi;
    }


    private Utente getAutoreById(int idAutore) throws SQLException {
        String sql = "SELECT * FROM UTENTE WHERE id_utente = ? AND funzione = 'Autore'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAutore);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Utente(
                        rs.getInt("id_utente"),
                        rs.getString("username"),
                        rs.getString("pass_word"),
                        rs.getString("funzione")
                );
            }
        }
        return null;
    }


}