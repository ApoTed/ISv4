package it.unibs.IngSftw4.mainClasses;

import java.util.ArrayList;

/**
 * Classe per la gestione della lista degli scambi
 */
public class ListaScambi {
    private ArrayList <Scambio> scambi=new ArrayList<>();

    /**
     * Costruttore della classe ListaScambi
     * @param _scambi la lista degli scambi
     */
    public ListaScambi(ArrayList <Scambio> _scambi){
        scambi=_scambi;
    }

    /**
     * Metodo per l'aggiunta di uno scambio agli scambi offerti
     * @param f il fruitore autore della proposta
     * @return la lista degli scambi offerti
     */
    public ListaScambi scambiOfferente(Fruitore f){
        ArrayList <Scambio> scambiOfferti=new ArrayList<>();
        for(Scambio s: this.scambi){
            if(s.getOfferente().getNomeFruitore().equals(f.getUsername())){
                scambiOfferti.add(s);
            }
        }
        return new ListaScambi(scambiOfferti);
    }
    /**
     * Metodo per l'aggiunta di uno scambio agli scambi ricevuti
     * @param f il fruitore ricevitore della proposta
     * @return la lista degli scambi ricevuti
     */
    public ListaScambi scambiRicevente(Fruitore f){
        ArrayList <Scambio> scambiRicevuti=new ArrayList<>();
        for(Scambio s: this.scambi){
            if(s.getRicevente().getNomeFruitore().equals(f.getUsername())){
                scambiRicevuti.add(s);
            }
        }
        return new ListaScambi(scambiRicevuti);
    }

    /**
     * Metodo che restituisce la lista degli scambi
     * @return la stringa degli scambi
     */
    public String vediScambi(){
        StringBuffer sb=new StringBuffer();
        int count=1;
        sb.append("Questi sono gli scambi che ti sono stati proposti\n");
        for(Scambio s:this.scambi){
            sb.append("\t");
            sb.append(count+") ");
            sb.append(s.vediOfferteScambio()+"\n");
            count++;
        }
        return sb.toString();
    }

    /**
     * Metodo per scegliere uno scambio da una lista di scambi
     * @return lo scambio scelto, null se nessuno scambio è stato scelto
     */
    public Scambio scegliScambio(){
        System.out.println(this.vediScambi());
        Scambio s=null;
        if(this.scambi.size()>0){
            int indice=Utilita.leggiIntero("Inserisci il numero relativo allo scambio di cui sei interessato, " +
                    "0 se non sei interessato: ",0,this.scambi.size());

            if(indice!=0){
                s=this.scambi.get(indice-1);
            }
        }
        return s;
    }

    /**
     * Metodo per aggiornare la lista degli scambi in base a quelli scaduti
     * @param ps i parametri degli scambi
     * @param offerte le offerte del sistema
     */
    public void controllaValiditaScambi(ParametriScambi ps,Offerte offerte){
        ArrayList <Scambio> temp=new ArrayList<>();
        for(Scambio s:this.scambi){
            if(!s.scambioScaduto(ps,offerte)){
                temp.add(s);
            }
        }
        this.scambi= temp;
    }

    /**
     * Metodo per aggiungere uno scambio
     * @param scambio lo scambio da aggiungere
     */
    public void addScambio(Scambio scambio){
        this.scambi.add(scambio);
    }

    /**
     * Metodo get per la lista degli scambi
     * @return la lista degli scambi
     */
    public ArrayList<Scambio> getScambi() {
        return scambi;
    }

    /**
     * Metodo per l'aggiornamento di uno scambio
     * @param sca lo scambio da aggiornare
     * @param index l'indice dello scambio
     */
    public void aggiornaScambio(Scambio sca, int index){
        this.scambi.remove(index);
        this.scambi.add(sca);
    }
}
