package it.unibs.IngSftw4.mainClasses;

import java.util.ArrayList;

public class ListaScambi {
    private ArrayList <Scambio> scambi=new ArrayList<>();

    public ListaScambi(ArrayList <Scambio> _scambi){
        scambi=_scambi;
    }

    public ListaScambi scambiOfferente(Fruitore f){
        ArrayList <Scambio> scambiOfferti=new ArrayList<>();
        for(Scambio s: this.scambi){
            if(s.getOfferente().getNomeFruitore().equals(f.getUsername())){
                scambiOfferti.add(s);
            }
        }
        return new ListaScambi(scambiOfferti);
    }
    public ListaScambi scambiRicevente(Fruitore f){
        ArrayList <Scambio> scambiRicevuti=new ArrayList<>();
        for(Scambio s: this.scambi){
            if(s.getRicevente().getNomeFruitore().equals(f.getUsername())){
                scambiRicevuti.add(s);
            }
        }
        return new ListaScambi(scambiRicevuti);
    }
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
     * metodo per scegliere uno scambio da una listascambi
     * @return lo scambio scelto, null se nessuno scamio è stato scelto
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

    public void controllaValiditaScambi(ParametriScambi ps){
        ArrayList <Scambio> temp=new ArrayList<>();
        for(Scambio s:this.scambi){
            if(!s.scambioScaduto(ps)){
                temp.add(s);
            }
        }
        this.scambi= temp;
    }

    public void addScambio(Scambio scambio){
        this.scambi.add(scambio);
    }

    public ArrayList<Scambio> getScambi() {
        return scambi;
    }
}
