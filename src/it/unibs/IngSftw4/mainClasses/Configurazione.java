package it.unibs.IngSftw4.mainClasses;

/**
 * Classe per la gestione della configurazione formata dal sistema e dai parametri di scambio
 * @author Jacopo Tedeschi,Enrico Zambelli
 */
public class Configurazione {
    private Sistema sis;
    private ParametriScambi parametri;
    /**
     * Costruttore di Configurazione
     * @param _sis il sistema della configurazione
     * @param _parametri i parametri della configurazione
     */
    public Configurazione(Sistema _sis,ParametriScambi _parametri){
        sis=_sis;
        parametri=_parametri;
    }
    /**
     * Metodo get per il sistema della Configurazione
     * @return il sistema
     */
    public Sistema getSis() {
        return sis;
    }
    /**
     * Metodo get per i parametri della configurazione
     * @return i parametri degli scambi
     */
    public ParametriScambi getParametri() {
        return parametri;
    }

    /**
     * Metodo per settare i parametri della configurazione
     * @param parametri i parametri da settare
     */
    public void setParametri(ParametriScambi parametri) {
        this.parametri = parametri;
    }

    /**
     * Metodo set per il sistema
     * @param sis il sistema da settare
     */
    public void setSis(Sistema sis) {
        this.sis = sis;
    }
}
