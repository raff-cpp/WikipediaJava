package Controller;


import Dao.UtenteDao;

import java.sql.Connection;
import java.sql.SQLException;

public class UserController {
    private UtenteDao utenteDAO;

    public UserController(Connection connection) {
        this.utenteDAO = new UtenteDao(connection);
    }



    public int getIdAutoreByModifica(int idModifica) {
        try {
            return utenteDAO.getIdAutoreByModifica(idModifica);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }



}
