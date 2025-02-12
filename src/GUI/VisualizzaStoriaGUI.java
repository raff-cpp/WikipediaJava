package GUI;

import Controller.PageController;
import Dao.PaginaWikiDao;
import model.PaginaWiki;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VisualizzaStoriaGUI extends JFrame {
    private Connection conn;
    private int idAutore;
    private PageController pageController;
    private JTextArea pagesTextArea;

    public VisualizzaStoriaGUI(Connection conn, int idAutore) {
        this.conn = conn;
        this.idAutore = idAutore;
        this.pageController = new PageController(conn);

        setTitle("Visualizza Pagine Create");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Pannello principale
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Lista delle pagine create
        pagesTextArea = new JTextArea();
        pagesTextArea.setEditable(false);
        caricaPagineCreate();


        panel.add(new JScrollPane(pagesTextArea), BorderLayout.CENTER);
        add(panel);
    }

    private void caricaPagineCreate() {
        PaginaWikiDao paginaWikiDao = new PaginaWikiDao(conn);
        try {
            List<PaginaWiki> pagine = paginaWikiDao.trovaPaginePerAutore(idAutore);
            StringBuilder contenuto = new StringBuilder();
            for (PaginaWiki pagina : pagine) {
                contenuto.append(pagina.getTitolo()).append("\n");
            }
            pagesTextArea.setText(contenuto.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
