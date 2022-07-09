package it.unibs.IngSftw4.mainClasses;

import java.util.Calendar;
/**
 * Classe per la gestione degli scambi
 */
public class Scambio {
    private Offerta offerente;
    private Offerta ricevente;
    private PropostaIncontro ultimaProposta;
    private long tempo;

    /**
     * Costruttore della classe Scambio
     * @param _offerente l'offerta dell'offerente
     * @param _ricevente l'offerta del destinatario
     * @param _ultimaProposta la proposta d'incontro più recente
     * @param _tempo tempo passata dalla proposta
     */
    public Scambio(Offerta _offerente, Offerta _ricevente, PropostaIncontro _ultimaProposta,long _tempo){
        offerente=_offerente;
        ricevente=_ricevente;
        ultimaProposta=_ultimaProposta;
        tempo=_tempo;
    }

    /**
     * Metodo per creare uno scambio
     * @param conf configurazione
     * @param tutteLeOfferte tutte le offerte presenti
     * @param f fruitore che propone lo scambio
     * @return uno scambio con PropostaIncontro null e se fallisce solo null
     */
    public static Scambio creaScambio(Configurazione conf, Offerte tutteLeOfferte,Fruitore f){
        System.out.println("Creazione di uno scambio\n");
        System.out.println("Scegli l'offerta che vuoi scambiare");
        Offerte offerteFruitore=new Offerte(tutteLeOfferte.getOfferteFromFruitore(f.getUsername()));
        offerteFruitore.tieniAperte();
        Offerta daScambiare=null;
        Offerta vorrei=null;
        Scambio toRet=null;
        if(offerteFruitore.getListaOfferte().size()>0){
            daScambiare=offerteFruitore.scegliOfferta();
            Offerte possibiliScambi=tutteLeOfferte.offerteScambiabili(daScambiare);
            if(possibiliScambi.getListaOfferte().size()>0){
                System.out.println("Scegli l'offerta con cui vorresti effettuare lo scambio: ");
                vorrei=possibiliScambi.scegliOfferta();
                tutteLeOfferte.modificaOffertaEsistente(vorrei, StatoOfferta.SELEZIONATA);
                vorrei.cambiaStato(StatoOfferta.SELEZIONATA);
                tutteLeOfferte.modificaOffertaEsistente(daScambiare, StatoOfferta.ACCOPPIATA);
                daScambiare.cambiaStato(StatoOfferta.ACCOPPIATA);
                PropostaIncontro p=null;
                long timeNow= Calendar.getInstance().getTimeInMillis();
                toRet=new Scambio(daScambiare,vorrei,p,timeNow);
                System.out.println("Scambio creato correttamente");
            }
            else{
                System.out.println("Non ci sono offerte con cui poter effettuare uno scambio data l'offerta selezionata");
            }
        }
        else{
            System.out.println("non hai offerte con cui proporre scambi");
        }
        return toRet;
    }

    /**
     * Metodo che restituisce un int in base allo stato dello scambio
     * @return 0 se non è neanche ora stata accettata l'offerta, 1 se è stato accettata e si stanno accordando sullo scambio
     * 2 se è scaduto il tempo e allora le offerte tornano aperte, 3 se i due fruitore si sono accordati sullo scambio
     */
    public int statoScambio(){
        int stato=0;
        if(offerente.getStatoAttuale()==StatoOfferta.ACCOPPIATA && ricevente.getStatoAttuale()==StatoOfferta.SELEZIONATA){
            stato=0;
        }
        else if(offerente.getStatoAttuale()==StatoOfferta.INSCAMBIO && ricevente.getStatoAttuale()==StatoOfferta.INSCAMBIO){
            stato=1;
        }
        else if(offerente.getStatoAttuale()==StatoOfferta.APERTA && ricevente.getStatoAttuale()==StatoOfferta.APERTA){
            stato=2;
        }
        else if(offerente.getStatoAttuale()==StatoOfferta.CHIUSA && ricevente.getStatoAttuale()==StatoOfferta.CHIUSA){
            stato=3;
        }
        return stato;
    }

    /**
     * Metodo per la gestione di uno scambio
     * @param f il fruitore che controlla lo stato dello scambio
     * @param ps parametri di scambio del sistema
     * @param offerte offerte del sistema
     */
    public void gestisciScambio(Fruitore f, ParametriScambi ps,Offerte offerte){
        switch (this.statoScambio()){
            case 0:
                if(f.getUsername().equals(offerente.getNomeFruitore())){
                    System.out.println("L'altro fruitore non ha neanche ora risposto alla tua proposta di scambio");
                }
                else{
                    System.out.println("Ti è stato proposto di scambiare questa offerta:\n"+this.offerente.toStringOfferta()+"\nCon questa tua offerta: \n"+ricevente.toStringOfferta());
                    int rispondi=Utilita.leggiIntero("Scrivi 1 se vuoi rispondere a questa offerta, 0 altrimenti",0,1);
                    if(rispondi==1){
                        PropostaIncontro nuovaPropposta=PropostaIncontro.creaProposta(f.getUsername(),ps);
                        this.cambiaProposta(nuovaPropposta);
                        this.ricevente.cambiaStato(StatoOfferta.INSCAMBIO);
                        this.offerente.cambiaStato(StatoOfferta.INSCAMBIO);
                        offerte.modificaOffertaEsistente(offerente, StatoOfferta.INSCAMBIO);
                        offerte.modificaOffertaEsistente(ricevente, StatoOfferta.INSCAMBIO);
                        this.tempo=nuovaPropposta.getTempo();
                }
                }
                break;
            case 1:
                if(this.ultimaProposta.getNomeFruitore().equals(f.getUsername())){
                    System.out.println("L'altro fruitore non ha ancora risposta alla tua proposta di incontro");
                }
                else{
                    System.out.println("L'ultima proposta di incontro è questa: \n"+this.ultimaProposta.visualizzaProposta());
                    int scelta=Utilita.leggiIntero("Se vuoi accettare questo incontro premi 1, se vuoi proporne un altro premi 2, se vuoi non rispondere premi 0",0,2);
                    if(scelta!=0){
                        if(scelta==1){
                            System.out.println("Lo scambio avverrà come scritto nella proposta che hai accettato: \n"+this.ultimaProposta.visualizzaProposta());
                            this.ricevente.cambiaStato(StatoOfferta.CHIUSA);
                            this.offerente.cambiaStato(StatoOfferta.CHIUSA);
                            offerte.modificaOffertaEsistente(ricevente, StatoOfferta.CHIUSA);
                            offerte.modificaOffertaEsistente(offerente, StatoOfferta.CHIUSA);
                        }
                        else{
                            if(scelta==2){
                                this.cambiaProposta(PropostaIncontro.creaProposta(f.getUsername(),ps));
                            }
                        }
                    }
                }
                break;
        }
    }

    /**
     * Metodo per settare l'ultima proposta d'incontro
     * @param pi
     */
    public void cambiaProposta(PropostaIncontro pi){
        this.ultimaProposta=pi;
    }

    /**
     * Metodo che controlla se uno scambio è scaduto e se lo è rimette le offerte nello stato aperta
     * @param ps parametri scambi in cui c'è la scadenza degli scambi
     * @return true se è scaduto, false altrimenti
     */
    public boolean scambioScaduto(ParametriScambi ps){
        boolean scaduto=false;
        long tempoOra=Calendar.getInstance().getTimeInMillis();
        long differenza=Utilita.compareInstants(this.tempo,tempoOra);
        if(differenza>ps.getScadenza()){
            scaduto=true;
            this.ricevente.cambiaStato(StatoOfferta.APERTA);
            this.offerente.cambiaStato(StatoOfferta.APERTA);
        }
        return scaduto;
    }

    /**
     * Metodo get per l'offerta dell'offerente
     * @return l'offerta del offerente
     */
    public Offerta getOfferente() {
        return offerente;
    }
    /**
     * Metodo get per l'offerta del destinatario
     * @return l'offerta del destinatario
     */
    public Offerta getRicevente() {
        return ricevente;
    }
    /**
     * Metodo get per l'ultima proposta d'incontro
     * @return l'ultima proposta d'incontro
     */
    public PropostaIncontro getUltimaProposta() {
        return ultimaProposta;
    }

    /**
     * Metodo get per il tempo trascorso dalla proposta di scambio
     * @return
     */
    public long getTempo() {
        return tempo;
    }

    /**
     * Metodo che restituisce una stringa con le informazioni relative alle offerte dello scambio
     * @return stringa con le informazioni
     */
    public String vediOfferteScambio(){
        StringBuffer sb=new StringBuffer();
        sb.append("Offerta offerente: \n");
        sb.append(offerente.toStringOffertaConAutore());
        sb.append("\n\tOfferta con cui si vorrebbe effettuare lo scambio:\n");
        sb.append(ricevente.toStringOffertaConAutore());
        return sb.toString();
    }
}
