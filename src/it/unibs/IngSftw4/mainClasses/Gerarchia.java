package it.unibs.IngSftw4.mainClasses;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe per la costruzione e la gestione della gerarchia
 * @author Enrico Zambelli, Jacopo Tedeschi
 */
public class Gerarchia {

    private HashMap <Categoria, Categoria> ramo= new HashMap<Categoria,Categoria>();
    private Categoria radice;

    /**
     * Costruttore della classe gerarchia
     * @param _ramo hashMap formata da una categoria e dalla sua categoria padre
     * @param _radice radice della gerarchia
     */
    public Gerarchia(HashMap <Categoria, Categoria> _ramo, Categoria _radice){
        this.ramo=_ramo;
        this.radice=_radice;
    }

    /**
     * Costruttore della classe gerarchia
     */
    public Gerarchia(){

    }

    /**
     * Metodo get per il ramo di una gerarchia
     * @return i ramo della categoria
     */
    public HashMap<Categoria, Categoria> getRamo() {
        return ramo;
    }

    /**
     * Metodo get per la radice di una gerarchia
     * @return la radice della gerarchia
     */
    public Categoria getRadice() {
        return radice;
    }

    /**
     * Metodo per la creazione della gerarchia da parte del configuratore
     * @param nomeRadice nome della radice che viene chiesto prima della creazione per verificarne la validità rispetto alle radici già presenti
     * @return finale la gerarchia creata
     */
    public static Gerarchia creaRamo(String nomeRadice){
        HashMap<Categoria, Categoria> links=new HashMap<Categoria,Categoria>();
        System.out.println("Inserisci i dati della gerarchia radice");
        CampoNativo primo=new CampoNativo("Stato di conservazione",true);
        CampoNativo primos=new CampoNativo("Descrizione libera",false);
        ArrayList <CampoNativo> campiIniziali=new ArrayList<>();
        campiIniziali.add(primo);
        campiIniziali.add(primos);
        Categoria fantoccio=new Categoria("inesistente","",campiIniziali);
        Gerarchia finale= new Gerarchia();
        Categoria r=Categoria.creaCategoria(fantoccio.getCampiNativi(),nomeRadice);
        finale.ramo.put(r,fantoccio);
        finale.setRadice(r);


        String choiceContinue=Utilita.leggiStringaNonVuota("Inserisci 1 se vuoi inserire una sottocategoria, 0 altrimenti:");
        while(choiceContinue.equals("1")){
            System.out.println(finale.vediPadri());

            boolean nomePadreValido=false;
            String nomePadre;
            Categoria padre = new Categoria("","",null);
            do{
                nomePadre=Utilita.leggiStringaNonVuota("Inserisci il nome del padre:");
                if(finale.checkPadreNome(nomePadre)){
                    nomePadreValido=true;
                    padre=finale.findPadre(nomePadre);
                }
                else {
                    System.out.println("Tale padre non esiste, scegli uno dei possibili padri:");
                    System.out.println(finale.vediPadri());
                }
            }while(nomePadreValido==false);
            boolean nomeNuovo=false;
            String nomeCatgoria;
            do{
                nomeCatgoria=Utilita.leggiStringaNonVuota("Inserisci il nome della categoria:");
                if(finale.checkNomeNuovo(nomeCatgoria)){
                    nomeNuovo=true;
                }
                else{
                    System.out.println("Nome non valido");
                }
            }while(!nomeNuovo);

            int figli=finale.numFigli(padre);
            if(figli==0){
                System.out.println("Si devono inserire almeno 2 sottocategorie perchè il padre non ne ha nessuna per ora");
                finale.ramo.put(Categoria.creaCategoria(finale.findPadre(nomePadre).getCampiNativi(),nomeCatgoria),finale.findPadre(nomePadre));
                //System.out.println("inserire la seconda sottocategoria di: "+nomePadre);
                String nomeCat2=Utilita.leggiStringaNonVuota("Inserire il nome della seconda sottocategoria di "+ nomePadre+": ");
                finale.ramo.put(Categoria.creaCategoria(finale.findPadre(nomePadre).getCampiNativi(),nomeCat2),finale.findPadre(nomePadre));
            }
            else{
                finale.ramo.put(Categoria.creaCategoria(finale.findPadre(nomePadre).getCampiNativi(),nomeCatgoria),finale.findPadre(nomePadre));
            }



            choiceContinue=Utilita.leggiStringaNonVuota("Inserisci 1 se vuoi inserire un'altra sottocategoria, 0 altrimenti:");
        }

        return finale;

    }

    /**
     * Metodo che controlla se un nome è già stato preso all'interno della gerarchia
     * @param s nome da controllare
     * @return nuovo boolean che è true se non è già presente false altrimenti
     */
    public boolean checkNomeNuovo(String s){
        boolean nuovo=true;
        for(Categoria x: this.ramo.keySet()){
            if(x.getNome().equals(s)){
                nuovo=false;
            }

        }
        return nuovo;
    }

    /**
     * Metodo che controlla quante sottocategorie ha una categoria
     * @param padre nome categoria da controllare
     * @return figli int che indica il numero di sottocategorie presenti
     */
    public int numFigli(Categoria padre){
        int figli=0;
        for(Categoria x:this.ramo.keySet()){
            if(this.ramo.get(x).getNome().equals(padre.getNome())){
                figli++;
            }
        }
        return figli;

    }


    /**
     * Metodo che restituisce la categoria padre inserendone il nome
     * @param nomePadre nome del padre
     * @return x la categoria padre
     */
    public Categoria findPadre(String nomePadre){
        for(Categoria x: this.ramo.keySet()){
            if(x.getNome().equalsIgnoreCase(nomePadre)){
                return x;
            }

        }
        return null;
    }

    /**
     * Metodo che restituisce una stringa con tutte le possibili categorie che possono avere delle sottocategorie
     * @return s.toString(); stringa contenente i nomi delle categorie
     */
    public String vediPadri(){
        StringBuffer s=new StringBuffer();
        for(Categoria x: this.ramo.keySet()){
            s.append(x.getNome());
            s.append(", ");
        }
        return s.toString();
    }

    /**
     * Metodo per impostare il valore della radice
     * @param radice categoria che si vuole impostare come radice
     */
    public void setRadice(Categoria radice) {
        this.radice = radice;
    }

    /**
     * Metodo che aggiunge una categoria con la relativa categoria padre
     * @param toAdd categoria da aggiungere
     * @param padre relativa categoria padre
     * @return la gerarchia modificate
     */
    public Gerarchia addCategoria(Categoria toAdd, Categoria padre){
        this.ramo.put(toAdd, padre);
        return this;
    }

    /**
     * Metodo per la visualizzazione della gerarchia
     * @return una stringa contenete le informazioni relative alla visualizzazione della gerarchia
     */
    public String vediRamo(){
        ArrayList<Categoria> nonVisti=new ArrayList<Categoria>();
        StringBuffer s=new StringBuffer();
        s.append("La radice è "+this.radice.getNome()+". ");
        for(Categoria x: this.ramo.keySet()){
            nonVisti.add(x);
        }
        nonVisti.remove(this.radice);
        ArrayList <Categoria> figliAlti=new ArrayList<Categoria>();
        for(Categoria x:this.ramo.keySet()){
            if(this.ramo.get(x).getNome().equals(this.radice.getNome())){
                figliAlti.add(x);
            }
        }
        if(this.numFigli(this.radice)==0){
            s.append("Non ha sottocategorie");
        }
        else{
            s.append("Le sottocategorie di "+this.radice.getNome()+ " sono: ");
        }

        for(Categoria x: figliAlti){
            s.append(x.getNome()+"   ");
        }

        do{
            s.append("\n");
            ArrayList<Categoria> figliBassi=new ArrayList<Categoria>();
            for(Categoria x: figliAlti){
                s.append("\n");
                ArrayList <Categoria> figlietti=new ArrayList<Categoria>();//aggiungi un invio qui
                for(Categoria y: this.ramo.keySet()){
                    if(this.ramo.get(y).getNome().equals(x.getNome())){
                        figlietti.add(y);
                    }
                }
                if(!figlietti.isEmpty()){
                    s.append("Le sottocategorie di "+x.getNome()+" sono: ");
                    for(Categoria j:figlietti){
                        s.append(j.getNome()+"    ");
                    }
                    figliBassi.addAll(figlietti);
                }
                else{
                    s.append(x.getNome()+" non ha sottocagorie");
                }



            }
            figliAlti.clear();
            figliAlti.addAll(figliBassi);

        }while(!figliAlti.isEmpty());
        return s.toString();


    }

    /**
     * Metodo che controlla che il nome inserito come categoria padre sia valido
     * @param nome nome della categoria padre di cui si vuole creare 1 o più sottocategorie
     * @return esiste boolean che è true se valido false altrimenti
     */
    public boolean checkPadreNome (String nome){
        boolean esiste=false;
        for(Categoria x: this.ramo.keySet()){
            if(x.getNome().equals(nome))
                esiste=true;
        }
        return esiste;
    }

    public ArrayList<Categoria> listaFoglie(){
        ArrayList <Categoria> foglie=new ArrayList<>();
        ArrayList <Categoria> dads=new ArrayList<>();
        dads=this.listaPadri();
        for(Categoria c: this.ramo.keySet()){
            if(!dads.contains(c)){
                foglie.add(c);
            }
        }
        return foglie;
    }

    /**
     * restituisce true se è un figlio altrimenti false;
     * @param toCheck Categoria da controllare
     * @return
     */
    public boolean isFoglia(Categoria toCheck){
        boolean answer=false;
        if(this.listaFoglie().contains(toCheck)){
            answer=true;
        }
        return answer;
    }


    public ArrayList<Categoria> listaPadri(){
        ArrayList <Categoria> padri=new ArrayList<>();
        for(Categoria c: this.ramo.keySet()){
            if(!padri.contains(this.ramo.get(c))){
                padri.add(this.ramo.get(c));
            }
        }
        return padri;
    }
}
