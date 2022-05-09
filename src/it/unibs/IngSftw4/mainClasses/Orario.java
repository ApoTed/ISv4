package it.unibs.IngSftw4.mainClasses;

/**
 * Classe per la gestione di un orario
 * @author Jacopo Tedeschi,Enrico Zambelli
 */
public class Orario {
    private int ora;
    private int minuti;

    /**
     * Costruttore della classe orario
     * @param _ora l'ora dell'orario
     * @param _minuti i minuti dell'orario
     */
    public Orario(int _ora, int _minuti){
        this.minuti=_minuti;
        this.ora =_ora;
    }
    /**
     * Metodo che controlla se un orario è valido(ora compresa tra 0 e 23,minuti uguali a 00 o 30)
     * @return true se l'orario è valido, false altrimenti
     */
    public boolean orarioValido(){
        boolean valido=false;
        if(this.minuti==0 || this.minuti==30){
            if(this.ora >=0 && this.ora <=24){
                valido=true;
            }
        }
        return valido;
    }

    /**
     * Metodo che applicato sull'orario controlla che sia all'interno dell'orario passato in ingresso
     * @param o1 orario inizio intervallo
     * @param o2 orario fine intervallo
     * @return true se è all'interno, false altrimenti
     */

    public boolean isInsideIntervallo(Orario o1, Orario o2){
        boolean dentro=false;
        if(o1.ora ==this.ora && this.ora ==o2.ora){
            dentro=true;
        }
        if(o1.ora <this.ora && this.ora >o2.ora){
            dentro=true;
        }
        if(this.ora ==o1.ora && this.ora <o2.ora){
            if(this.minuti>=o1.minuti){
                dentro=true;
            }
        }
        if(this.ora >o1.ora && this.ora ==o2.ora){
            if(this.minuti<=o2.minuti){
                dentro=true;
            }
        }
        return dentro;
    }
    /**
     * Metodo che restituisce l'orario corrispondente alla stringa in ingresso
     * @param ora la stringa da cui ricavare l'orario
     * @return l'orario corrispondete alla stringa
     */
    public static Orario getOrarioFromString(String ora){
        StringBuffer sb=new StringBuffer();
        sb.append(ora.charAt(0));
        sb.append(ora.charAt(1));
        int o=Integer.valueOf(sb.toString());
        StringBuffer sbm=new StringBuffer();
        sbm.append(ora.charAt(3));
        sbm.append(ora.charAt(4));
        int m=Integer.valueOf(sbm.toString());
        Orario or=new Orario(o,m);
        return or;
    }
    /**
     * Metodo che restituisce una stringa corrispondente all'orario
     * @return la stringa dell'orario su cui viene invocato
     */
    public String toStringOrario(){
        StringBuilder str = new StringBuilder();
        if(this.ora <10){
            str.append("0"+this.ora);
        }
        else{
            str.append(this.ora);
        }

        if(this.minuti==0){
            str.append(":00");
        }
        else{
            str.append(":30");
        }

        return str.toString();
    }
    /**
     * Metodo get per l'ora di un orario
     * @return l'ora dell'orario
     */
    public int getOra() {
        return ora;
    }
    /**
     * Metodo get per i minuti di un orario
     * @return i minuti dell'orario
     */
    public int getMinuti() {
        return minuti;
    }

}

