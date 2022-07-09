package it.unibs.IngSftw4.mainClasses;


import java.util.*;

/**
 * Classe contente vari metodi statici di utilità
 * @author Jacopo Tedeschi,Enrico Zambelli
 */
public class Utilita {
    private static final String ERRORE_FORMATO = "Attenzione il dato inserito non e' nel formato corretto";
    private static final String ERRORE_MINIMO = "Attenzione: e' richiesto un valore maggiore o uguale a ";
    private static final String ERRORE_MASSIMO = "Attenzione: e' richiesto un valore minore o uguale a ";
    public static final String CATEGORIA_NON_PRESENTE = "Categoria non presente";
    public static final String ERRORE_STRINGA_VUOTA = "La stringa inserita non può essere vuota";
    private static Scanner lettore = creaScanner();


    /**
     * Metodo per la creazione di uno scanner
     * @return lo scanner creato
     */
    private static Scanner creaScanner() {
        Scanner creato = new Scanner(System.in);
        creato.useDelimiter(System.getProperty("line.separator"));
        creato.useDelimiter("\n");
        return creato;
    }

    /**
     * Metodo per la lettura di una stringa in input
     * @param messaggio il messaggio da visualizzare
     * @return la stringa inserita
     */
    public static String leggiStringa(String messaggio) {
        System.out.print(messaggio);
        return lettore.next();
    }

    /**
     * Metodo per la lettura di una stringa non vuota in input
     * @param messaggio  il messaggio da visualizzare
     * @return la stringa inserita
     */
    public static String leggiStringaNonVuota(String messaggio) {
        boolean finito = false;
        String lettura = null;
        do {
            lettura = leggiStringa(messaggio);
            lettura = lettura.trim();
            if (lettura.length() > 0)
                finito = true;
            else
                System.out.println(ERRORE_STRINGA_VUOTA);
        } while (!finito);

        return lettura;
    }

    /**
     * Metodo per la lettura di un intero in input
     * @param messaggio  il messaggio da visualizzare
     * @return l'intero inserito
     */
    public static int leggiIntero(String messaggio) {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            System.out.print(messaggio);
            try {
                valoreLetto = lettore.nextInt();
                finito = true;
            } catch (InputMismatchException e) {
                System.out.println(ERRORE_FORMATO);
                String daButtare = lettore.next();
            }
        } while (!finito);
        return valoreLetto;
    }

    /**
     * Metodo per la lettura di un intero in input , con massimo e minimo
     * @param messaggio il messaggio da visualizzare
     * @param minimo il minimo valore da inserire
     * @param massimo il massimo valore da inserire
     * @return il valore inserito
     */
    public static int leggiIntero(String messaggio, int minimo, int massimo) {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            valoreLetto = leggiIntero(messaggio);
            if (valoreLetto >= minimo && valoreLetto <= massimo)
                finito = true;
            else if (valoreLetto < minimo)
                System.out.println(ERRORE_MINIMO + minimo);
            else
                System.out.println(ERRORE_MASSIMO + massimo);
        } while (!finito);

        return valoreLetto;
    }

    /**
     * Metodo per la lettura di una categoria in input
     * @param sistema il sistema in cui cercare la categoria
     * @return la categoria cercata, null se non è presente
     */
    public static Categoria leggiCategoria(Sistema sistema){
        Categoria trovata=null;
        int numCat=Utilita.leggiIntero("Inserisci il numero della gerarchia alla quale appartiene la categoria che vuoi vedere nel dettaglio: ", 1, sistema.getListaGerarchie().size());
        String nome = leggiStringaNonVuota("Inserisci il nome della categoria da visualizzare: ");
        trovata = sistema.findCategoria(nome, numCat);
        if (trovata == null) {
            System.out.println(CATEGORIA_NON_PRESENTE);
        }
        return trovata;
    }

    /**
     * metodo che controlla che la data inserita sia uno dei giorni della settimana disponibili
     * @param dataStringa data in forma di stringa
     * @param listaGiorniSettimana lista di giorni della settimana disponibili in numero della settimana
     * @return true se è prsente false altrimenti
     */
    public static boolean checkGiornoSettimana(String dataStringa, ArrayList<Integer> listaGiorniSettimana){
        Calendar c=Calendar.getInstance(Locale.ITALY);
        int year=Integer.valueOf(dataStringa.substring(0,4));
        int mm=Integer.valueOf(dataStringa.substring(5,7));
        int day=Integer.valueOf(dataStringa.substring(8,10));
        c.set(year,mm,day);
        boolean valido=false;
        int i= c.get(Calendar.DAY_OF_WEEK);
        if(listaGiorniSettimana.contains(i))
            valido=true;
        return valido;
    }

    /**
     * Metodo per l'inserimento di una data
     * @return la stringa corrispondente alla data inserita
     */
    public static String inserisciData(){
        StringBuffer sb=new StringBuffer();
        int anno=Utilita.leggiIntero("Inserisci l'anno per esteso: ",2021,2100);
        sb.append(Integer.toString(anno));
        sb.append("-");
        if(anno % 400 == 0 || anno %4 == 0 && anno % 100 != 0){
            sb.append(inserisciGiornoMese(true));
        }
        else{
            sb.append(inserisciGiornoMese(false));
        }
        return sb.toString();
    }

    /**
     * Metodo per l'inserimento di un giorno del mese
     * @param bisestile boolean che indica se l'anno è bisestile o no
     * @return la stringa relativa al giorno del mese
     */
    public static String inserisciGiornoMese(boolean bisestile){
        int meseNum=Utilita.leggiIntero("Inserisci il numero del mese: ", 1,12);
        StringBuffer meseGiorno=new StringBuffer();
        if(meseNum<10){
            meseGiorno.append("0");
            meseGiorno.append(Integer.toString(meseNum));
        }
        else{
            meseGiorno.append(Integer.toString(meseNum));
        }
        int giorniMassimi=0;
        if(meseNum==9 || meseNum==4 || meseNum==6 || meseNum==11){
            giorniMassimi=30;
        }else if(meseNum==2){
            if(bisestile)
                giorniMassimi=29;
            else
                giorniMassimi=28;
        }
        else{
            giorniMassimi=31;
        }
        meseGiorno.append("-");
        int giorno=Utilita.leggiIntero("Inserisci il giorno del mese: ",1,giorniMassimi);
        if(giorno<10){
            meseGiorno.append("0");
            meseGiorno.append(Integer.toString(giorno));
        }
        else
            meseGiorno.append(Integer.toString(giorno));
        return meseGiorno.toString();
    }

    /**
     * Metodo che restituisce la differenza tra due istanti in giorni
     * @param old istante vecchio
     * @param nuovo instante nuovo
     * @return nuovo-old
     */
    public static long compareInstants(long old, long nuovo){
        return  ((nuovo/(1000*60*60*24))-(old/(1000*60*60*24)));
    }

}

