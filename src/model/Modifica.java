package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Modifica {
    private int idModifica;
    private LocalTime oraModifica;
    private LocalDate dataModifica;
    private String nuovoTesto;
    private String vecchioTesto;
    private StatoModifica stato;
    private Utente proponente;
    private Utente autoreOriginale;
    private int idFrase;





    public Modifica(int idModifica, String nuovoTesto, String vecchioTesto, StatoModifica stato, int idUtente, int idFrase) {
        this.idModifica = idModifica;
        this.nuovoTesto = nuovoTesto;
        this.vecchioTesto = vecchioTesto;
        this.stato = stato;
        this.proponente = new Utente(idUtente);
        this.idFrase = idFrase;
    }





    public Modifica(int idModifica, LocalTime oraModifica, LocalDate dataModifica, String nuovoTesto, String vecchioTesto, StatoModifica stato, Utente proponente) {
        this.idModifica = idModifica;
        this.oraModifica = oraModifica;
        this.dataModifica = dataModifica;
        this.nuovoTesto = nuovoTesto;
        this.vecchioTesto = vecchioTesto;
        this.stato = stato;
        this.proponente = proponente;
    }


    public int getIdFrase() {
        return idFrase;
    }

    public void setIdFrase(int idFrase) {
        this.idFrase = idFrase;
    }

    public int getIdModifica() {
        return idModifica;
    }


    public LocalTime getOraModifica() {
        return oraModifica;
    }


    public LocalDate getDataModifica() {
        return dataModifica;
    }


    public String getNuovoTesto() {
        return nuovoTesto;
    }

    public void setNuovoTesto(String nuovoTesto) {
        this.nuovoTesto = nuovoTesto;
    }

    public String getVecchioTesto() {
        return vecchioTesto;
    }

    public void setVecchioTesto(String vecchioTesto) {
        this.vecchioTesto = vecchioTesto;
    }

    public StatoModifica getStato() {
        return stato;
    }

    public void setStato(StatoModifica stato) {
        this.stato = stato;
    }

    public Utente getProponente() {
        return proponente;
    }

    public void setProponente(Utente proponente) {
        this.proponente = proponente;
    }

    public Utente getAutoreOriginale() {
        return autoreOriginale;
    }

    public void setAutoreOriginale(Utente autoreOriginale) {
        this.autoreOriginale = autoreOriginale;
    }
}


