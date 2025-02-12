package Controller;

import Dao.ModificaDao;
import model.Modifica;
import model.StatoModifica;
import model.Utente;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class ProposeEditController {
    private ModificaDao modificaDao;

    public ProposeEditController(Connection conn) {
        this.modificaDao = new ModificaDao(conn);
    }

    public boolean submitEdit(String nuovoTesto, String vecchioTesto, int paginaId, int utenteId, int idFrase) {
        try {
            // Ottieni la data e l'ora correnti
            Timestamp timestamp = new Timestamp(new Date().getTime());

            // Crea una nuova modifica
            Modifica modifica = new Modifica(
                    0, // ID verrà generato dal database
                    timestamp.toLocalDateTime().toLocalTime(), // Ora della modifica
                    timestamp.toLocalDateTime().toLocalDate(), // Data della modifica
                    nuovoTesto,
                    vecchioTesto, // Vecchio testo
                    StatoModifica.PROPOSTA,
                    new Utente(utenteId, "", "", "") // Proponente
            );

            // Salva la modifica nel database includendo l'ID della frase
            return modificaDao.salvaModifica(modifica, idFrase);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




}