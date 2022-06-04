package it.unibs.IngSftw4.mainClasses;

/**
 * Classe enum per la gestione dei giorni
 *  @author Jacopo Tedeschi,Enrico Zambelli
 */
public enum Giorno {
    LUNEDI,
    MARTEDI,
    MERCOLEDI,
    GIOVEDI,
    VENERDI,
    SABATO,
    DOMENICA;

    /**
     * Metodo che restituisce la stringa corrispondente al giorno su cui viene invocata
     * @return la stringa del giorno su cui viene invocata
     */
    public String toString(){
        String day=null;
        switch(this){
            case LUNEDI :
                day="lunedì";
                break;
            case MARTEDI:
                day="martedì";
                break;
            case MERCOLEDI:
                day="mercoledì";
                break;
            case GIOVEDI:
                day="giovedì";
                break;
            case VENERDI:
                day="venerdì";
                break;
            case SABATO:
                day="sabato";
                break;
            case DOMENICA:
                day="domenica";
                break;

        }
        return day;
    }
    /**
     * Metodo che restituisce il giorno corrispondente alla stringa in ingresso
     * @param gg la stringa di cui restituire il giorno corrispondente
     * @return il giorno corrispondete alla stringa in ingresso se presente, null altrimenti
     */
    public static Giorno getGiornoFromString(String gg){

        switch(gg){
            case "lunedì":
                return LUNEDI;
            case "martedì":
                return MARTEDI;

            case "mercoledì":
                return MERCOLEDI;

            case "giovedì":
                return GIOVEDI;

            case "venerdì":
                return VENERDI;

            case "sabato":
                return SABATO;

            case "domenica":
                return DOMENICA;

        }
        return null;
    }

    /**
     * Metodo che restituisce il giorno in base a un intero
     * @return giorno corrispondente al numero passato in ingresso
     */
    public int getNumFromDay(){
        int numOfTheWeek=0;
        switch(this){
            case LUNEDI :
                numOfTheWeek=5;
                break;
            case MARTEDI:
                numOfTheWeek=6;
                break;
            case MERCOLEDI:
                numOfTheWeek=7;
                break;
            case GIOVEDI:
                numOfTheWeek=1;
                break;
            case VENERDI:
                numOfTheWeek=2;
                break;
            case SABATO:
                numOfTheWeek=3;
                break;
            case DOMENICA:
                numOfTheWeek=4;
                break;

        }
        return numOfTheWeek;
    }
}

