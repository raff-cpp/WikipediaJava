package Controller;

import DatabaseConnection.*;
import Dao.ReputazioneDao;
import Dao.UtenteDao;

import java.sql.Connection;
import java.sql.SQLException;

public class RegisterController {
    private UtenteDao utenteDao;
    private ReputazioneDao reputazioneDao;


    public RegisterController() {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            this.utenteDao = new UtenteDao(conn);
            this.reputazioneDao = new ReputazioneDao(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la connessione al database", e);
        }
    }

    public boolean registraUtente(String funzione, String username, String password) {
        try {

            int idUtente = utenteDao.creaUtente(funzione, username, password);


            if (idUtente != -1) {
                reputazioneDao.resettaReputazioneByIdUtente(idUtente);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}