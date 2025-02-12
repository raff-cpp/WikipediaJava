package model;

public class Link {
    private Frase fraseOrigine;
    private PaginaWiki paginaDestinazione;

    public Link(Frase fraseOrigine, PaginaWiki paginaDestinazione) {
        this.fraseOrigine = fraseOrigine;
        this.paginaDestinazione = paginaDestinazione;
    }

    public Frase getFraseOrigine() {
        return fraseOrigine;
    }

    public PaginaWiki getPaginaDestinazione() {
        return paginaDestinazione;
    }
}