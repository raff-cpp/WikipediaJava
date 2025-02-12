package Controller;

import Dao.PaginaWikiDao;
import model.Frase;
import model.PaginaWiki;

import java.sql.Connection;
import java.sql.SQLException;

public class PageController {
    private PaginaWikiDao paginaWikiDao;

    public PageController(Connection conn) {
        this.paginaWikiDao = new PaginaWikiDao(conn);
    }

    public PaginaWiki getPaginaByTitolo(String titolo) throws SQLException {
        return paginaWikiDao.getPaginaByTitolo(titolo);
    }

    public Frase getTestoPagina(int idPagina) throws SQLException {
        return paginaWikiDao.getFrasiByPaginaId(idPagina);
    }


}