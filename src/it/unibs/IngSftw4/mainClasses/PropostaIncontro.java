package it.unibs.IngSftw4.mainClasses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PropostaIncontro {
    private String nomeFruitore;
    private String luogo;
    private Orario ora;
    private String data;
    private long tempo;

    public PropostaIncontro(String _nomeFruitore, String _luogo, Orario _ora, String _data, long _tempo){
        nomeFruitore=_nomeFruitore;
        luogo=_luogo;
        ora=_ora;
        data=_data;
        tempo=_tempo;
    }

    public static PropostaIncontro creaProposta(String fruitoreNome, ParametriScambi ps){
        System.out.println("Adesso potrai creare la tua proposta per l'incontro con l'altro fruitore ");
        String luogoScelto=ps.scegliLuogo();
        boolean giornoCorretto=false;
        String dataTemp="";
        while(!giornoCorretto){
            ArrayList <Giorno> giorniTemP=new ArrayList();
            System.out.println("I giorni della settiaman in cui è possibile effettuare uno scambio sono: ");
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
        do{
            System.out.println("Adesso inserisci a che ora: ");
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
            }
        }while(!compreso);
        long timeNow= Calendar.getInstance().getTimeInMillis();
        PropostaIncontro proposta=new PropostaIncontro(fruitoreNome, luogoScelto, inizio, dataTemp,timeNow);
        return proposta;
    }
}
