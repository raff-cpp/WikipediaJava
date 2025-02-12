package model;

import java.sql.Date;
import java.sql.Time;

public class PaginaWiki {
    private int idPagina;
    private String titolo;
    private String frasi;
    private Date dataCreazione;
    private Time oraCreazione;
    private Utente utente;




    public PaginaWiki(int idPagina, String titolo, Date dataCreazione, Time oraCreazione, Utente idAutore) {
        this.idPagina = idPagina;
        this.titolo = titolo;
        this.dataCreazione = dataCreazione;
        this.oraCreazione = oraCreazione;
        this.utente = idAutore;
    }



    public int getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(int idPagina) {
        this.idPagina = idPagina;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getFrasi() {
        return frasi;
    }

    public void setFrasi(String frasi) {
        this.frasi = frasi;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }



}