package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class FraseDao {
    private Connection connection;

    public FraseDao(Connection connection) {
        this.connection = connection;
    }



    public void aggiornaTestoFrase(int idFrase, String nuovoTesto) throws SQLException {
        String query = "UPDATE FRASE f\n" +
                "SET testo_frase = ?\n" +
                "FROM MODIFICA m\n" +
                "WHERE f.id_frase = m.id_frase\n" +
                "AND f.id_frase = ?;\n";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nuovoTesto);
            stmt.setInt(2, idFrase);
            stmt.executeUpdate();
        }
    }
}