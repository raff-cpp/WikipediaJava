package Controller;


import Dao.ReputazioneDao;

import java.sql.Connection;
import java.sql.SQLException;

public class ReputazioneController {
    private ReputazioneDao reputazioneDao;

    public ReputazioneController(Connection conn) {
        this.reputazioneDao = new ReputazioneDao(conn);
    }

    public String getReputazioneByIdUtente(int idUtente) {
        try {
            return reputazioneDao.getReputazioneByIdUtente(idUtente);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void incrementaModificheProp(int idUtente) {
        try {
            reputazioneDao.incrementaModificheProp(idUtente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void incrementaModificheAcc(int idUtente) {
        try {
            reputazioneDao.incrementaModificheAcc(idUtente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}