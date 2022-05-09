package it.unibs.IngSftw4.mainClasses;

/**
 * Classe per la gestione di un'intervallo di orari
 *  @author Jacopo Tedeschi,Enrico Zambelli
 */
public class Intervallo {

    private Orario[] ore = new Orario[2];

    /**
     * Costruttore d'Intervallo
     * @param _ore i due orari che definiscono l'intervallo
     */
    public Intervallo(Orario[] _ore) {
        this.ore = _ore;
    }
    //System.currentTimeMillis();

    /**
     * Metodo get per gli orari dell'intervallo
     * @return gli orari dell'intervallo
     */
    public Orario[] getOre() {
        return ore;
    }

    /**
     * Metodo che controlla se un intervallo è valido(ora d'inizio minore di quella di della di fine, oppure uguale con i muniti dell'inizio uguali a 00 e quelli della fine uguali a 30)
     * @return true se l'intervallo è valido, false altrimenti
     */
    public boolean intervalloValido(){
        boolean valido=false;
        if(this.ore[0].getOra()==this.ore[1].getOra()){
            if(this.ore[0].getMinuti()==0 && this.ore[1].getMinuti()==30){
                valido=true;
            }
        }
        if(this.ore[0].getOra() <this.ore[1].getOra())
            valido=true;
        return valido;
    }
    /**
     * Metodo che crea un intervallo ricevendo in ingresso gli orari che lo compongono
     * @param o gli orari che compongono l'intervallo
     * @return l'intervallo creato
     */
    public static Intervallo creaIntervallo (Orario [] o){
        Intervallo i=new Intervallo(o);
        return  i;
    }

    /**
     * Metodo per controllare che due intervalli siano uguali
     * @param toCompare l'intervallo con cui confrontare quello invocato
     * @return true se i due intervalli sono uguali, false altrimenti
     */
    public boolean compareIntervallo(Intervallo toCompare) {
        boolean uguale=false;
        if (this.ore[0].getOra() == toCompare.ore[0].getOra() && this.ore[0].getMinuti() == toCompare.ore[0].getMinuti() &&
                this.ore[1].getOra()==toCompare.ore[1].getOra() && this.ore[1].getMinuti()==toCompare.ore[1].getMinuti()) {
            uguale = true;
        }
        return uguale;
    }
    /**
     * Metodo per settare gli orari di un intervallo
     * @param ore gli orari con cui settare l'intervallo
     */
    public void setOre(Orario[] ore) {
        this.ore = ore;
    }
}

