package it.unibs.IngSftw4.mainClasses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe per la gestione delle proposte d'incontro
 */
public class PropostaIncontro {
    private String nomeFruitore;
    private String luogo;
    private Orario ora;
    private String data;
    private long tempo;

    /**
     * Costruttore della classe PropostaIncontro
     * @param _nomeFruitore nome del fruitore autore della proposta
     * @param _luogo luogo della proposta
     * @param _ora ora della proposta
     * @param _data data della proposta
     * @param _tempo tempo passato dalla creazione della proposta
     */
    public PropostaIncontro(String _nomeFruitore, String _luogo, Orario _ora, String _data, long _tempo){
        nomeFruitore=_nomeFruitore;
        luogo=_luogo;
        ora=_ora;
        data=_data;
        tempo=_tempo;
    }

    /**
     * Metodo per la creazione di una proposta d'incontro
     * @param fruitoreNome nome del fruitore autore
     * @param ps parametri degli scambi
     * @return la proposta creata
     */
    public static PropostaIncontro creaProposta(String fruitoreNome, ParametriScambi ps){
        System.out.println("Adesso potrai creare la tua proposta per l'incontro con l'altro fruitore ");
        String luogoScelto=ps.scegliLuogo();
        boolean giornoCorretto=false;
        String dataTemp="";
        while(!giornoCorretto){
            ArrayList <Giorno> giorniTemP=new ArrayList();
            System.out.println("I giorni della settimana in cui è possibile effettuare uno scambio sono: ");
            for(Giorno g:ps.getGiorni()) {
                if (!giorniTemP.contains(g)) {
                    giorniTemP.add(g);
                    System.out.print(g.toString() + "\t");
                }
            }
            System.out.print("\n");
            dataTemp=Utilita.inserisciData();
            if(Utilita.checkGiornoSettimana(dataTemp,ps.giorniInInteri())){
                giornoCorretto=true;
            }
            if(!giornoCorretto){
                System.out.println("Il giorno inserito non fa parte dei giorni disponibili, i giorni disponibili sono: \n");
                for(Giorno gg: giorniTemP){
                    System.out.print(gg.toString() + "\t");
                }

            }
        }
        boolean compreso=false;
        Orario inizio=null;
        System.out.println("Adesso inserisci a che ora: ");
        do{
            System.out.println(ps.vediIntervalli());
            boolean finito = false;
            do {
                int ora = Utilita.leggiIntero("Inserisci l'ora dell'inizio dell'intervallo(compresa tra 0 e 24):");
                int minuti = Utilita.leggiIntero("Inserisci i minuti dell'inizio dell'intervallo(0 oppure 30):");
                inizio = new Orario(ora, minuti);
                if (inizio.orarioValido()) {
                    finito = true;
                } else {
                    System.out.println("L'orario inserito non è valido");
                }
            } while (!finito);
            for(Intervallo i:ps.getIntervalli()){
                if(inizio.isInsideIntervallo(i.getOre()[0], i.getOre()[1]))
                    compreso=true;
                else
                    System.out.println("L'orario inserito non rientra in un intervallo");
            }
        }while(!compreso);
        long timeNow= Calendar.getInstance().getTimeInMillis();
        PropostaIncontro proposta=new PropostaIncontro(fruitoreNome, luogoScelto, inizio, dataTemp,timeNow);
        System.out.println("Creazione proposta terminata con successo");
        return proposta;
    }

    /**
     * Metodo che restituisce la stringa relativa a una proposta
     * @return la stringa relativa alla proposta
     */
    public String visualizzaProposta(){
        StringBuffer sb=new StringBuffer();
        sb.append("\nIl luogo proposto è: "+ this.luogo+ " alle ore: "+this.ora.toStringOrario()+"\nIn data: "+ this.data);
        return sb.toString();
    }

    /**
     * Metodo get per il tempo trascorso dalla creazione della proposta
     * @return il tempo trascorso dalla creazione della proposta
     */
    public long getTempo() {
        return tempo;
    }
    /**
     * Metodo get per il luogo della proposta
     * @return il luogo della proposta
     */
    public String getLuogo() {
        return luogo;
    }
    /**
     * Metodo get per il nome del fruitore autore della proposta
     * @return il nome del fruitore autore della proposta
     */
    public String getNomeFruitore() {
        return nomeFruitore;
    }
    /**
     * Metodo get per l'ora della proposta
     * @return l'ora della proposta
     */
    public Orario getOra() {
        return ora;
    }
    /**
     * Metodo get per la data della proposta
     * @return la data della proposta
     */
    public String getData() {
        return data;
    }
}
