package it.unibs.IngSftw4.mainClasses;


import java.util.ArrayList;

/**
 * Classe per la gestione di una categoria.
 * @author Jacopo Tedeschi, Enrico Zambelli
 */
public class Categoria {
    private String nome;
    private String descrizione;
    private ArrayList <CampoNativo> campiNativi= new ArrayList <CampoNativo>();

    /**
     * Costruttore della classe Categoria
     * @param _nome il nome della categoria
     * @param _descrizione la descrizione della categoria
     * @param _campiNativi la lista dei campi nativi della categoria
     */
    public Categoria (String _nome, String _descrizione, ArrayList <CampoNativo> _campiNativi ) {

        this.nome=_nome;
        this.descrizione=_descrizione;
        this.campiNativi=_campiNativi;
    }


    /**
     * Metodo per la creazione di una categoria con i suoi campi nativi
     * @param campi i campi nativi della categoria padre che vengono ereditati
     * @param nomeCat il nome della categoria
     * @return la categoria creata
     */
    public static Categoria creaCategoria(ArrayList <CampoNativo> campi, String nomeCat){//devo ritornare una categoria

        ArrayList <CampoNativo> copia=new ArrayList<>();
        copia.addAll(campi);
        String choice="1";
        String descrizione=Utilita.leggiStringaNonVuota("Inserisci la descrizione della categoria:");
        Categoria creata=new Categoria("","", null);
        while(choice.equals("1")){
            choice=Utilita.leggiStringaNonVuota("Inserisci 1 se vuoi aggiungere un nuovo campo, 0 altrimenti: ");

            if(choice.equals("1")) {
                ArrayList<String> nomi = new ArrayList<String>();
                for (CampoNativo x : copia) {
                    nomi.add(x.getNomeCampo());
                }
                copia.add(CampoNativo.creaCampo(nomi));
            }
            else
                System.out.println("Questa categoria ha " + copia.size() + " campi");

        }
        creata=new Categoria(nomeCat,descrizione, copia);


        return creata;

    }

    /**
     * Metodo per la visualizzazione di una categoria
     * @return  nomeCategoria : descrizione \n Campi Nativi : \n listaCampi
     */
    public String toStringCategoria() {
        StringBuffer str = new StringBuffer();
        str.append("Il nome è: "+this.nome + " e la sua descrizione è: " + this.descrizione + "\n");

        if (!campiNativi.isEmpty()) {
            str.append("Campi Nativi : \n");
            for (CampoNativo cn : campiNativi) {
                str.append("\t"+cn.toString());
            }

        }
        return str.toString();
    }

    /**
     * Metodo get per la lista dei campi nativi
     * @return la lista dei campi nativi della categoria
     */
    public ArrayList<CampoNativo> getCampiNativi() {
        return campiNativi;
    }

    /**
     * Metodo get per il nome della categoria
     * @return il nome della categoria
     */
    public String getNome() {
        return nome;
    }
    /**
     * Metodo get per la descrizione della categoria
     * @return la descrizione della categoria
     */
    public String getDescrizione() {
        return descrizione;
    }
}


