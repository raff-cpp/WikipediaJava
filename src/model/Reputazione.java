package model;

public class Reputazione {
    private int nPagine;
    private int nModificheAcc;
    private int nModificheProp;

    public Reputazione(int nPagine, int nModificheAcc, int nModificheProp) {
        this.nPagine = nPagine;
        this.nModificheAcc = nModificheAcc;
        this.nModificheProp = nModificheProp;
    }

    public int getnPagine() {
        return nPagine;
    }

    public void setnPagine(int nPagine) {
        this.nPagine = nPagine;
    }

    public int getnModificheAcc() {
        return nModificheAcc;
    }

    public void setnModificheAcc(int nModificheAcc) {
        this.nModificheAcc = nModificheAcc;
    }

    public int getnModificheProp() {
        return nModificheProp;
    }

    public void setnModificheProp(int nModificheProp) {
        this.nModificheProp = nModificheProp;
    }

}