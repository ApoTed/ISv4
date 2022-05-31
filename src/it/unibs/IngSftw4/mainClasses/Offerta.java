package it.unibs.IngSftw4.mainClasses;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe per la gestione di un offerta
 */
public class Offerta {

    private String nomeCategoria;
    private String nomeRadice;
    private HashMap <CampoNativo, String> compliazioni=new HashMap<>();
    private StatoOfferta statoAttuale;
    private ArrayList <StatoOfferta> statiPassati=new ArrayList<>();
    private String nomeFruitore;

    /**
     * Costruttore della classe offerta
     * @param _nomeCategoria nome della categoria a cui appartiene l'offerta
     * @param _compilazioni le compilazioni dei campi nativi della categoria
     * @param _statoAttuale lo stato attuale dell'offerta
     * @param _nomeFruitore il nome utente del fruitore autore dell'offerta
     * @param _statiPassati gli stati passati dell'offerta
     * @param _nomeRadice il nome della categoria radice della gerarchia a cui appartiene la categoria
     */
    public Offerta( String _nomeCategoria, HashMap <CampoNativo, String> _compilazioni,StatoOfferta _statoAttuale,String _nomeFruitore,ArrayList<StatoOfferta> _statiPassati, String _nomeRadice ){
        this.nomeCategoria=_nomeCategoria;
        this.compliazioni=_compilazioni;
        this.statoAttuale=_statoAttuale;
        this.nomeFruitore=_nomeFruitore;
        this.statiPassati=_statiPassati;
        this.nomeRadice=_nomeRadice;
    }

    /**
     * Costruttore senza parametri della classe Offerta
     */
    public Offerta() {
    }

    /**
     * Metodo per la creazione di un'offerta
     * @param s il sistema dell'applicazione
     * @param nome il nome dell'articolo inserito
     * @return true se la creazione è andata a buon fine, false altrimenti
     */
    public boolean creaOfferta(Sistema s,String nome){
        boolean successo=false;
        System.out.println(s.toStringSistema());
        boolean continua=true;
        do{
            int ger=Utilita.leggiIntero("Inserisci il numero della gerarchia dove si trova la categoria scelta:",1,s.getListaGerarchie().size());
            String n=Utilita.leggiStringaNonVuota("Inserisci il nome della categoria dove vuoi pubblicare il tuo articolo: ");
            Categoria c=s.findCategoria(n,ger);
            if(c!=null && s.isFoglia(c.getNome())){
                this.nomeRadice=s.getListaGerarchie().get(ger-1).getRadice().getNome();
                this.nomeCategoria=c.getNome();
                this.compilaCampi(c);
                this.statoAttuale=StatoOfferta.APERTA;
                this.nomeFruitore=nome;
                this.statiPassati=new ArrayList<StatoOfferta>();
                continua=false;
                successo=true;
            }
            else{
                int temp=Utilita.leggiIntero("La categoria inserita non esiste o non è una foglia, se vuoi riprovare premi 1 altrimenti 0:",0,1);
                if(temp==0)
                    continua=false;
            }
        }while(continua);

        return successo;
    }

    /**
     * Metodo per effettuare la compilazione dei campi
     * @param c la categoria di cui compilare i campi nativi
     */
    public void compilaCampi(Categoria c){
        for(CampoNativo camp:c.getCampiNativi()){
            if(camp.isObbligatoria()){
                String descr=Utilita.leggiStringaNonVuota("Inserisci la descrizione relativa al campo "+camp.getNomeCampo()+" : ");
                this.compliazioni.put(camp, descr);
            }
            else{
                int sce=Utilita.leggiIntero("Se vuoi inserire una descrizione al campo "+camp.getNomeCampo()+" inserisci 1 altrimenti 0:",0,1);
                String descr="";
                if(sce==1){
                    descr=Utilita.leggiStringaNonVuota("Inserisci la descrizione relativa al campo "+camp.getNomeCampo()+" : ");
                }
                this.compliazioni.put(camp, descr);
            }
        }
        System.out.println("Fine compilazione campi");
    }

    /**
     * Metodo per effettuare il cambio di stato di un'offerta
     */
    public void cambiaStato(StatoOfferta so){
        this.statiPassati.add(this.statoAttuale);
        this.statoAttuale=so;
    }

    /**
     *
     * @param toCompare
     * @return
     */
    public boolean confrontaOfferta(Offerta toCompare){
        boolean uguale=false;
        if(this.nomeCategoria.equals(toCompare.getNomeCategoria()) && this.nomeRadice.equals(toCompare.nomeRadice)){
            if(this.nomeFruitore.equals(toCompare.nomeFruitore)){
                boolean temp=false;
                ArrayList <String> compilazioniConNome=new ArrayList<>();
                for(CampoNativo cTemp: this.compliazioni.keySet()){
                    StringBuffer sb=new StringBuffer();
                    sb.append(cTemp.getNomeCampo());
                    sb.append(this.compliazioni.get(cTemp));
                    compilazioniConNome.add(sb.toString());
                }
                int count=0;
                for(CampoNativo cToCompare: toCompare.compliazioni.keySet()){
                    StringBuffer sb2=new StringBuffer();
                    sb2.append(cToCompare.getNomeCampo());
                    sb2.append(toCompare.compliazioni.get(cToCompare));
                    String ts=sb2.toString();
                    if(compilazioniConNome.contains(ts)){
                        count++;
                    }
                }
                if(count==compilazioniConNome.size())
                    temp=true;
                if(temp==true){
                    uguale=true;
                }
            }
        }
        return uguale;
    }

    /**
     * Metodo get per il nome della radice
     * @return il nome della radice
     */
    public String getNomeRadice() {
        return nomeRadice;
    }

    /**
     * Metodo che restituisce una stringa che descrive le compilazioni
     * @return la stringa che descrive le compilazioni
     */
    public String toStringCompilazioni(){
        StringBuffer stb = new StringBuffer();
        for(CampoNativo c: compliazioni.keySet()){
            stb.append("\t"+c.getNomeCampo() + ": " + compliazioni.get(c) + "\n");
        }
        return stb.toString();
    }

    /**
     * Metodo che restituisce una stringa che descrive l'offerta senza l'autore
     * @return la stringa che descrive l'offerta
     */
    public String toStringOfferta(){
        StringBuffer stb = new StringBuffer();
        stb.append(" Categoria: " + this.getNomeCategoria()+"\n");
        stb.append(this.toStringCompilazioni());
        stb.append("\tStato dell'offerta: " + this.getStatoAttuale().toStringStato());

        return stb.toString();
    }

    /**
     * Metodo per visualizzare l'offerta e anche il relativo autore
     * @return la stringa con le informazioni sull'offerta autore incluso
     */
    public String toStringOffertaConAutore(){
        StringBuffer stb=new StringBuffer();
        stb.append(this.toStringOfferta());
        stb.append("\n\tAutore offerta: " + this.getNomeFruitore()+"\n");
        return stb.toString();
    }

    /**
     * Metodo get per gli stati passati
     * @return gli stati passati dell'offerta
     */
    public ArrayList<StatoOfferta> getStatiPassati() {
        return statiPassati;
    }
    /**
     * Metodo get per il nome della categoria
     * @return il nome della categoria
     */
    public String getNomeCategoria() {
        return nomeCategoria;
    }
    /**
     * Metodo get per la HashMap delle compilazioni
     * @return la HashMap delle compilazioni
     */
    public HashMap<CampoNativo, String> getCompilazioni() {
        return compliazioni;
    }
    /**
     * Metodo get per lo stato attuale
     * @return lo stato attuale dell'offerta
     */
    public StatoOfferta getStatoAttuale() {
        return statoAttuale;
    }

    /**
     * Metodo get per il nome del fruitore
     * @return il nome del fruitore autore dell'offerta
     */
    public String getNomeFruitore() {
        return nomeFruitore;
    }

}
