package model;

public class Utente {
    private int idUtente;
    private String username;
    private String password;
    private String funzione;


    public Utente(int idUtente, String username, String password, String funzione) {
        this.idUtente = idUtente;
        this.username = username;
        this.password = password;
        this.funzione = funzione;
    }



    public Utente() {

    }

    public Utente(int idUtente) {
        this.idUtente= idUtente;
    }

    public Utente(int idUtente, String username) {
        this.idUtente= idUtente;
        this.username = username;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFunzione() {
        return funzione;
    }

    public void setFunzione(String funzione) {
        this.funzione = funzione;
    }

}