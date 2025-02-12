package GUI;

import Controller.PageController;
import Controller.ProposeEditController;
import model.Frase;
import model.PaginaWiki;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class UtenteGUI extends JFrame {
    private PageController pageController;
    private ProposeEditController proposeEditController;
    private Utente utente;
    private Connection conn;

    private JTextField searchField;
    private JTextArea pageContentArea;

    private int idFraseCorrente = -1;

    public UtenteGUI(Connection conn, Utente utente) {
        this.conn = conn;
        this.utente = utente;
        this.pageController = new PageController(conn);
        this.proposeEditController = new ProposeEditController(conn);

        setTitle("Utente Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout());

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Cerca");
        searchButton.addActionListener(e -> searchPage());

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Cerca Pagina:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        panel.add(searchPanel, BorderLayout.NORTH);

        pageContentArea = new JTextArea();
        pageContentArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(pageContentArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton proposeEditButton = new JButton("Proponi Modifica");
        proposeEditButton.addActionListener(e -> proposeEdit());
        panel.add(proposeEditButton, BorderLayout.SOUTH);

        add(panel);
    }

    private void searchPage() {
        String searchText = searchField.getText().trim();
        if (!searchText.isEmpty()) {
            try {
                PaginaWiki pagina = pageController.getPaginaByTitolo(searchText);

                if (pagina != null) {
                    Frase frase = pageController.getTestoPagina(pagina.getIdPagina());
                    if (frase != null) {
                        pageContentArea.setText(frase.toString());
                        idFraseCorrente = frase.getIdFrase(); // Salva l'id della frase corrente
                    } else {
                        pageContentArea.setText("Nessun contenuto trovato per questa pagina.");
                        idFraseCorrente = -1;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Pagina non trovata!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore durante la ricerca della pagina.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Inserisci un termine di ricerca!");
        }
    }

    private void proposeEdit() {
        String currentContent = pageContentArea.getText();
        if (!currentContent.isEmpty() && idFraseCorrente != -1) {
            new ProposeEditGUI(currentContent, utente, 1, idFraseCorrente, conn).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Nessuna pagina o frase selezionata!");
        }
    }
}
