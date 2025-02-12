package Controller;

import Dao.ModificaDao;
import model.Modifica;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ModificationController {
    private ModificaDao modificaDao;

    public ModificationController(Connection conn) {
        this.modificaDao = new ModificaDao(conn);
    }

    public List<Modifica> getModificheProposte() {
        try {
            return modificaDao.getModificheProposte();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean accettaModifica(int idModifica) {
        try {
            return modificaDao.accettaModifica(idModifica);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rifiutaModifica(int idModifica) {
        return modificaDao.rifiutaModifica(idModifica);
    }


}