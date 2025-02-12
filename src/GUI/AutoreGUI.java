package GUI;

import Controller.FraseController;
import Controller.ModificationController;
import Controller.ReputazioneController;
import Controller.UserController;
import model.Modifica;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class AutoreGUI extends JFrame {
    private ModificationController modificationController;
    private FraseController fraseController;
    private ReputazioneController reputazioneController;
    private UserController userController;
    private Connection conn;
    private int idAutoreLoggato;

    private JList<Modifica> modificheList;
    private DefaultListModel<Modifica> listModel;
    private JTextArea dettagliTextArea;
    private JLabel reputazioneLabel;
    private JButton accettaButton, rifiutaButton;

    public AutoreGUI(Connection conn , int idAutoreLoggato) {
        this.conn = conn;
        this.userController = new UserController(conn);
        this.idAutoreLoggato = idAutoreLoggato;
        this.modificationController = new ModificationController(conn);
        this.fraseController = new FraseController(conn);
        this.reputazioneController = new ReputazioneController(conn);

        setTitle("Autore Dashboard");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Pannello per le modifiche proposte
        JPanel modifichePanel = new JPanel(new BorderLayout(10, 10));
        modifichePanel.setBorder(BorderFactory.createTitledBorder("Modifiche Proposte"));

        listModel = new DefaultListModel<>();
        modificheList = new JList<>(listModel);
        modificheList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modificheList.addListSelectionListener(e -> mostraDettagliModifica());
        JScrollPane modificheScrollPane = new JScrollPane(modificheList);
        modifichePanel.add(modificheScrollPane, BorderLayout.CENTER);

        // Pannello per dettagli modifica
        dettagliTextArea = new JTextArea(5, 30);
        dettagliTextArea.setEditable(false);
        dettagliTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        dettagliTextArea.setBackground(new Color(245, 245, 245));
        JScrollPane dettagliScrollPane = new JScrollPane(dettagliTextArea);

        // Pannello per reputazione
        JPanel reputazionePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        reputazionePanel.setBorder(BorderFactory.createTitledBorder("Reputazione Utente"));
        reputazioneLabel = new JLabel("Seleziona una modifica per vedere la reputazione.");
        reputazionePanel.add(reputazioneLabel);

        // Aggiunta dei dettagli e reputazione al pannello
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(dettagliScrollPane, BorderLayout.CENTER);
        infoPanel.add(reputazionePanel, BorderLayout.SOUTH);

        modifichePanel.add(infoPanel, BorderLayout.SOUTH);

        // Pannello per i pulsanti

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Cambiamo il layout per allineare i bottoni a sinistra
        accettaButton = new JButton("✔ Accetta");
        rifiutaButton = new JButton("✖ Rifiuta");
        JButton visualizzaPagineButton = new JButton("Visualizza Pagine Create");

        accettaButton.setBackground(new Color(144, 238, 144));
        rifiutaButton.setBackground(new Color(255, 102, 102));
        visualizzaPagineButton.setBackground(new Color(173, 216, 230)); // Aggiungi uno sfondo chiaro per il bottone

        accettaButton.addActionListener(e -> accettaModifica());
        rifiutaButton.addActionListener(e -> rifiutaModifica());
        visualizzaPagineButton.addActionListener(e -> apriVisualizzaStoriaGUI()); // Azione per aprire la nuova GUI

        buttonPanel.add(accettaButton);
        buttonPanel.add(rifiutaButton);
        buttonPanel.add(visualizzaPagineButton); // Aggiungi il nuovo bottone
        modifichePanel.add(buttonPanel, BorderLayout.NORTH);

        mainPanel.add(modifichePanel, BorderLayout.CENTER);
        add(mainPanel);

        caricaModificheProposte();

    }

    private void apriVisualizzaStoriaGUI() {
        // Crea una nuova finestra per visualizzare la storia
        VisualizzaStoriaGUI visualizzaStoriaGUI = new VisualizzaStoriaGUI(conn, idAutoreLoggato);
        visualizzaStoriaGUI.setVisible(true); // Mostra la finestra
    }


    private void caricaModificheProposte() {
        listModel.clear();
        List<Modifica> modifiche = modificationController.getModificheProposte();

        for (Modifica modifica : modifiche) {
            int idAutore = userController.getIdAutoreByModifica(modifica.getIdModifica());

            // Mostra solo le modifiche appartenenti all'autore loggato
            if (idAutore == idAutoreLoggato) {
                listModel.addElement(modifica);
            }
        }
    }




    private void mostraDettagliModifica() {
        Modifica selectedModifica = modificheList.getSelectedValue();
        if (selectedModifica != null) {
            dettagliTextArea.setText("ID: " + selectedModifica.getIdModifica() + "\n" +
                    "Vecchio Testo: " + selectedModifica.getVecchioTesto() + "\n" +
                    "Nuovo Testo: " + selectedModifica.getNuovoTesto() + "\n" +
                    "Stato: " + selectedModifica.getStato() + "\n" +
                    "Utente Proponente: " + selectedModifica.getProponente().getIdUtente());


            // Recupera la reputazione dell'utente che ha proposto la modifica
            if (selectedModifica.getProponente() != null) {
                int idUtente = selectedModifica.getProponente().getIdUtente();
                String reputazione = reputazioneController.getReputazioneByIdUtente(idUtente);
                reputazioneLabel.setText("Reputazione Utente : " + reputazione);
            } else {
                reputazioneLabel.setText("Informazioni sul proponente non disponibili.");
            }
        } else {
            reputazioneLabel.setText("Seleziona una modifica per vedere la reputazione.");
        }
    }

    private void accettaModifica() {
        Modifica selectedModifica = modificheList.getSelectedValue();

        if (selectedModifica != null) {
            // Accetta la modifica nel database
            modificationController.accettaModifica(selectedModifica.getIdModifica());
            JOptionPane.showMessageDialog(this, "Modifica accettata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

            // Incrementa la reputazione dell'utente che ha proposto la modifica
            if (selectedModifica.getProponente() != null) {
                int idUtente = selectedModifica.getProponente().getIdUtente();
                reputazioneController.incrementaModificheAcc(idUtente);
            }

            // Aggiorna il testo della frase associata alla modifica
            if (selectedModifica.getIdFrase() > 0) {
                fraseController.aggiornaFrase(selectedModifica.getIdFrase(), selectedModifica.getNuovoTesto());
            } else {
                JOptionPane.showMessageDialog(this, "Errore: ID frase non valido!", "Errore", JOptionPane.ERROR_MESSAGE);
            }


            caricaModificheProposte();
        } else {
            JOptionPane.showMessageDialog(this, "Seleziona una modifica prima di accettare!", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rifiutaModifica() {
        Modifica selectedModifica = modificheList.getSelectedValue();
        if (selectedModifica != null) {
            modificationController.rifiutaModifica(selectedModifica.getIdModifica());
            JOptionPane.showMessageDialog(this, "Modifica rifiutata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            caricaModificheProposte();
        } else {
            JOptionPane.showMessageDialog(this, "Seleziona una modifica prima di rifiutare!", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}

