package GUI;

import Controller.ProposeEditController;
import Controller.ReputazioneController;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ProposeEditGUI extends JFrame {
    private ProposeEditController proposeEditController;
    private ReputazioneController reputazioneController;
    private Utente utente;
    private int paginaId;
    private int idFrase;
    private Connection conn;
    private String vecchioTesto;

    public ProposeEditGUI(String currentContent, Utente utente, int paginaId, int idFrase, Connection conn) {
        this.utente = utente;
        this.paginaId = paginaId;
        this.idFrase = idFrase;
        this.conn = conn;
        this.vecchioTesto = currentContent;
        this.proposeEditController = new ProposeEditController(conn);
        this.reputazioneController = new ReputazioneController(conn);
        initComponents(currentContent);
    }

    private void initComponents(String currentContent) {
        setTitle("Proponi Modifica");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JTextArea originalContent = new JTextArea(currentContent);
        originalContent.setEditable(false);
        panel.add(new JScrollPane(originalContent), BorderLayout.NORTH);

        JTextArea editContent = new JTextArea();
        panel.add(new JScrollPane(editContent), BorderLayout.CENTER);

        JButton submitButton = new JButton("Invia Modifica");
        submitButton.addActionListener(e -> {
            String nuovoTesto = editContent.getText();
            if (proposeEditController.submitEdit(nuovoTesto, vecchioTesto, paginaId, utente.getIdUtente(), idFrase)) {
                reputazioneController.incrementaModificheProp(utente.getIdUtente());

                JOptionPane.showMessageDialog(this, "Modifica inviata con successo!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Errore durante l'invio della modifica.");
            }
        });

        panel.add(submitButton, BorderLayout.SOUTH);
        add(panel);
    }
}
