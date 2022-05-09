package it.unibs.IngSftw4.mainClasses;


import java.util.InputMismatchException;
import java.util.Scanner;

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

}

