package it.unibs.IngSftw4.mainClasses;


import java.util.ArrayList;

/**
 * Classe per la gestione del sistema
 * @author Jacopo Tedeschi, Enrico Zambelli
 */
public class Sistema {


    private ArrayList <Gerarchia> listaGerarchie=new ArrayList<Gerarchia>();

    /**
     * Costruttore della classe Sistema
     * @param _listaGerarchie la lista delle gerarchie del sistema
     */
    public Sistema(ArrayList<Gerarchia>_listaGerarchie){
        this.listaGerarchie=_listaGerarchie;
    }

    /**
     * Metodo per l'aggiunta di una gerarchia al sistema
     * @param g la gerarchia da aggiungere
     */
    public void addGerarchia(Gerarchia g){
        this.listaGerarchie.add(g);
    }

    /**
     * Metodo per la visualizzazione di un sistema
     * @return la stringa corrispondente alla descrizione del sistema
     */
    public String toStringSistema(){
        StringBuffer stb=new StringBuffer();
        if(listaGerarchie.isEmpty()){
            stb.append("Il sistema non ha alcuna gerarchia");
            return stb.toString();
        }
        int i=1;
        for(Gerarchia g : listaGerarchie){
            stb.append("Gerarchia " + i +":\n");
            stb.append(g.vediRamo()+"\n");
            stb.append("\n");
            i++;
        }
        return stb.toString();
    }

    /**
     * Metodo gt per la lista delle gerarchie
     * @return la lista delle gerarchie del sistema
     */
    public ArrayList<Gerarchia> getListaGerarchie() {
        return listaGerarchie;
    }

    /**
     * Metodo per la ricerca di una categoria nel sistema in base al nome passato in ingresso
     * @param nome il nome della categoria da cercare
     * @return la categoria cercata se presente, null altrimenti
     */
    public Categoria findCategoria(String nome, int numGer){
        for(Categoria x: this.listaGerarchie.get(numGer-1).getRamo().keySet()){
            if(x.getNome().equalsIgnoreCase(nome)){
                return x;
            }
        }
        return null;
    }

    /**
     * Metodo per il controllo del nome di una radice nel sistema
     * @param s il nome della radice da controllare
     * @return true se il nome della radice non è presente nel sistema, false altrimenti
     */
    public boolean checkNomeNuovoRadice(String s){
        boolean valido=true;
        for(Gerarchia x:this.listaGerarchie){
            if(x.getRadice().getNome().equalsIgnoreCase(s)){
                valido=false;
            }
        }
        return valido;
    }

    /**
     * Metodo che restituisce la lista delle radici del sistema
     * @return la lista delle radici del sistema su cui viene invocato
     */
    public ArrayList<Categoria> getListaRadici(){
        ArrayList<Categoria> radici=new ArrayList<Categoria>();
        for(Gerarchia g:this.listaGerarchie){
            radici.add(g.getRadice());
        }
        return radici;
    }


    /**
     * Metodo per la visualizzazione delle radici del sistema
     * @return la stringa con le informazioni sulle radici del sistema
     */
    public String visualizzaRadici(){
        StringBuffer str=new StringBuffer();
        if(this.getListaGerarchie().size()==0){
            str.append("Siamo spiacenti ma il configuratore non ha settato alcuna gerarchia per ora");
        }
        else{
            if(this.getListaGerarchie().size()==1){
                str.append("Esiste una sola gerarchia e questa è la sua radice: \n"+this.getListaGerarchie().get(0).getRadice().toStringCategoria());
            }
            else{
                str.append("Le radici di ogni gerarchia sono:\n ");
                int count=0;
                for(Gerarchia x:this.getListaGerarchie()){
                    str.append("1. "+x.getRadice().toStringCategoria()+"\n");
                }
            }
        }
        return str.toString();
    }

    /**
     * Metodo per sceglie un offerta foglia
     * @return una array di Categoria contente all'indice 0 la categoria foglia scelta
     * e all'indice 1 la categoria radice relativa alla categoria foglia scelta, se la selezione fallisce ritorna null a entrambi gli indici
     */
    public Categoria [] scegliFoglia() {
        Categoria[] questaRadice = new Categoria[2];
        int count = 1;
        for (Gerarchia g : this.listaGerarchie) {
            System.out.print("\n" + count+")  ");
            System.out.println(g.getRadice().toStringCategoria());
            count++;
        }
        int sceltaGer = Utilita.leggiIntero("Scegli il numero rispettivo alla categoria radice da cui vuoi partire a cercare la categoria voluta: ", 0, this.listaGerarchie.size());
        boolean fineScelta = false;
        ArrayList <Categoria> foglie=this.listaGerarchie.get(sceltaGer-1).listaFoglie();
        int countF=1;
        System.out.println();
        for(Categoria f:foglie){
            System.out.print(countF+")  ");
            System.out.println(f.toStringCategoria());
            countF++;
        }
        int sceltaFoglia = Utilita.leggiIntero("Inserisci il numero della categoria, se nessuna ti va bene premi 0 e si annulla l'operazione corrente: ", 0, foglie.size());
        if(sceltaFoglia!=0){
            questaRadice[0]=foglie.get(sceltaFoglia-1);
            questaRadice[1]=this.listaGerarchie.get(sceltaGer-1).getRadice();
        }
        else{
            System.out.println("Selezione categoria annullata");
            Categoria fake=null;
            questaRadice[0]=null;
            questaRadice[1]=null;
        }
        return questaRadice;
    }

    /**
     *
     * @param nomeCat
     * @return
     */
    public boolean isFoglia(String nomeCat){
        ArrayList <Categoria> foglie=new ArrayList<>();
        boolean isFoglia=false;
        for(Gerarchia g:this.listaGerarchie){
            foglie.addAll(g.listaFoglie());
        }
        for(Categoria c:foglie){
            if(c.getNome().equals(nomeCat))
                isFoglia=true;
        }
        return isFoglia;
    }

}

