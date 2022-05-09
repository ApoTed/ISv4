package it.unibs.IngSftw4.mainClasses;

import java.util.ArrayList;

/**
 * classe per la gestione di tutte le offerte
 * @author Enrico Zambelli, Jacopo Tedeschi
 */
public class Offerte {
    private ArrayList<Offerta> listaOfferte= new ArrayList<Offerta>();

    /**
     * costruttore della calsse
     * @param _offerteAccoppiate arraylist delle offerte
     */
    public Offerte( ArrayList<Offerta> _offerteAccoppiate) {
        this.listaOfferte=_offerteAccoppiate;
    }

    /**
     * metodo che aggiunge un offerta alla liste delle offerte
     * @param o offerta da aggiungere
     */
    public void addOffertaAunFruitore( Offerta o){
        this.listaOfferte.add(o);
    }

    /**
     * metodo che restituisce la lista delle offerte
     * @return arraylist delle offerte
     */
    public ArrayList<Offerta> getListaOfferte() {
        return listaOfferte;
    }

    /**
     * metodo che restituisce le offerte appartenenti ad un fruitore
     * @param nomeFruiotore nome del fruitore di cui si vogliono le offerte
     * @return arrayList di offerte che appartengono al fruitore dato
     */
    public ArrayList<Offerta> getOfferteFromFruitore(String nomeFruiotore){
        ArrayList <Offerta> toReturn=new ArrayList<>();
        for(Offerta o: this.listaOfferte){
            if(o.getNomeFruitore().equals(nomeFruiotore)){
                toReturn.add(o);
            }
        }
        return toReturn;
    }

    /**
     * metodo che retituisce una stringa con le informazioni di tutte le offerte
     * @return String con informazioni sulle offerte
     */
    public  String toStringOfferte(){
        StringBuffer s=new StringBuffer();
        int count=0;
        for(Offerta o:this.listaOfferte){
            s.append("\n"+count +") " );
            s.append(o.toStringOfferta());
            count++;
        }
        return s.toString();
    }

    /**
     * metodo per scegliere un offerta
     * @return Offerta scelta
     */
    public Offerta scegliOfferta(){
        System.out.println("Queste sono le tue offerte : ");
        System.out.println(this.toStringOfferte());
        int scelta=Utilita.leggiIntero("scegli il numero relativo all'offerta scelta: ",0, this.listaOfferte.size());
        return this.listaOfferte.get(scelta);
    }

    /**
     * metodo per cambiare lo stato di una offerta esistente
     * @param toChange offerta di cui si vuole cambiare lo stato
     */
    public void modificaOffertaEsistente(Offerta toChange){
        int indice=this.listaOfferte.indexOf(toChange);
        Offerta temp=this.listaOfferte.get(indice);
        temp.cambiaStato();
        this.listaOfferte.remove(indice);
        this.listaOfferte.add(temp);
    }

    /**
     * metodo che elimina le offerte nell statoOfferta RITIRATA
     */
    public void togliRitirate(){
        ArrayList <Offerta> temp=new ArrayList<>();
        for(Offerta o: this.listaOfferte){
            if(o.getStatoAttuale()==StatoOfferta.APERTA.APERTA){
                temp.add(o);
            }
        }
        this.listaOfferte.clear();
        this.listaOfferte.addAll(temp);
    }

    /**
     * metodo per ottenere tutte le offerte realtive ad una categoria foglia
     * @param nomeFoglia nome della categoria foglia di cui si vogliono sapere le offerte
     * @return Offerte con tutte le offerte relative alla categoria
     */
    public Offerte offerteFoglia(String nomeFoglia){
        ArrayList <Offerta> toRet=new ArrayList<>();
        for(Offerta o:this.listaOfferte){
            if(o.getNomeCategoria().equals(nomeFoglia)){
                toRet.add(o);
            }
        }
        Offerte ret=new Offerte(toRet);
        if(!toRet.isEmpty()){
            ret.togliRitirate();
        }
        return ret;
    }

    /**
     * stampa tutte le offerte relative ad una categoria foglia da selezionare
     * @param conf Configurazione contente il sistema
     */
    public void stampaOfferteFoglia(Configurazione conf){
        Categoria [] categoriaFoglia= conf.getSis().scegliFoglia();
        if(categoriaFoglia[0] != null){
            Offerte tosee=this.offerteFoglia(categoriaFoglia[0].getNome());
            if(tosee.getListaOfferte().size()!=0){
                System.out.println("Le offerte di questa categoria sono: ");
                System.out.println(tosee.toStringOfferte());
            }
            else{
                System.out.println("non ci sono offerte aperte realtive a questa categoria");
            }
        }
        else{
            System.out.println("Visualizzazione offerte fallita");
        }
    }

}

