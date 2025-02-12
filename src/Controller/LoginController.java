package Controller;

import Dao.UtenteDao;
import model.Utente;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginController {
    private UtenteDao utenteDao;

    public LoginController(Connection conn) {
        this.utenteDao = new UtenteDao(conn);
    }

    public Utente autenticaUtente(String username, String password) throws SQLException {

        Utente utente = utenteDao.getUtenteByUsername(username);

        if (utente != null && utente.getPassword().equals(password)) {
            return utente;
        }
        return null;
    }

    public String getFunzioneByUsername(String username) {

        try {
            return utenteDao.getFunzioneByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
