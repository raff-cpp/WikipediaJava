package model;

public class Frase {
    private int idFrase;
    private String testoFrase;

    public Frase(int idFrase, String testoFrase) {
        this.idFrase = idFrase;
        this.testoFrase = testoFrase;
    }

    public int getIdFrase() {
        return idFrase;
    }

    public void setIdFrase(int idFrase) {
        this.idFrase = idFrase;
    }

    public String getTestoFrase() {
        return testoFrase;
    }

    public void setTestoFrase(String testoFrase) {
        this.testoFrase = testoFrase;
    }

    public String toString(){
        return this.testoFrase + "\n";
    }
}