package Controller;

import Dao.FraseDao;

import java.sql.Connection;
import java.sql.SQLException;

public class FraseController {
    private FraseDao fraseDao;

    public FraseController(Connection connection) {
        this.fraseDao = new FraseDao(connection);
    }

    public void aggiornaFrase(int idFrase, String nuovoTesto) {
        try {
            fraseDao.aggiornaTestoFrase(idFrase, nuovoTesto);
            System.out.println("Frase aggiornata con successo.");
        } catch (SQLException e) {
            System.err.println("Errore nell'aggiornamento della frase: " + e.getMessage());
        }
    }

}
