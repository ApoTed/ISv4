package it.unibs.IngSftw4.mainClasses;

/**
 * enum per la gestione degli stati dell'offerta
 * @ author Jacopo Tedeschi, Enrico Zambelli
 */
public enum StatoOfferta {
    RITIRATA,
    ACCOPPIATA,
    SELEZIONATA,
    INSCAMBIO,
    CHIUSA,
    APERTA;

    /**
     * metodo che restituisce la stringa che descrive lo stato
     * @return stringa che descrive lo stato
     */
    public String toStringStato(){
        String s=null;
        switch(this){
            case APERTA:
                s="aperta";
            break;
            case RITIRATA:
                s="ritirata";
            break;
            case INSCAMBIO:
                s="in scambio";
            break;
            case ACCOPPIATA:
                s="accoppiata";
            break;
            case CHIUSA:
                s="chiusa";
            break;
            case SELEZIONATA:
                s="selezionata";
            break;
        }
        return s;
    }

    /**
     * metodo che restituisce lo stato dell'offerta da una stringa che descrive lo stato
     * @param stato stringa che descrive lo stato
     * @return
     */
    public static StatoOfferta getStatoFromString(String stato){
        StatoOfferta s=null;
        switch(stato){
            case "aperta":
                 s=StatoOfferta.APERTA;
                break;
            case "ritirata":
                s=StatoOfferta.RITIRATA;
                break;
            case "in scambio":
                s=StatoOfferta.INSCAMBIO;
                break;
            case "accoppiata":
               s=StatoOfferta.ACCOPPIATA;
                break;
            case "chiusa":
                s=StatoOfferta.CHIUSA;
                break;
            case "selezionata":
                s=StatoOfferta.SELEZIONATA;
                break;
        }
        return s;
    }
}
