package it.unibs.IngSftw4.mainClasses;

/**
 * enum per la gestione degli stati dell'offerta
 * @ author Jacopo Tedeschi, Enrico Zambelli
 */
public enum StatoOfferta {
    RITIRATA,
    APERTA;

    /**
     * metodo che restituisce la stringa che descrive lo stato
     * @return stringa che descrive lo stato
     */
    public String toStringStato(){
        String s=null;
        if(this== RITIRATA){
            s="ritirata";
        }
        else{
            s="aperta";
        }
        return s;
    }

    /**
     * metodo che restituisce lo stato dell'offerta da una stringa che descrive lo stato
     * @param stato stringa che descrive lo stato
     * @return
     */
    public static StatoOfferta getStatoFromString(String stato){
        if(stato.equals("ritirata")){
            return RITIRATA;
        }
        else
            return APERTA;
    }
}
