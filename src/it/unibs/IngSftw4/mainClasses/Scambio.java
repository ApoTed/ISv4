package it.unibs.IngSftw4.mainClasses;

import java.util.Calendar;

public class Scambio {
    private Offerta offerente;
    private Offerta ricevente;
    private PropostaIncontro ultimaProposta;
    private long tempo;

    public Scambio(Offerta _offerrente, Offerta _ricevente, PropostaIncontro _ultimaProposta,long _tempo){
        offerente=_offerrente;
        ricevente=_ricevente;
        ultimaProposta=_ultimaProposta;
        tempo=_tempo;
    }
    public Scambio creaScambio(Configurazione conf, Offerte tutteLeOfferte,Fruitore f){

        Offerte offerteFruitore=new Offerte(tutteLeOfferte.getOfferteFromFruitore(f.getUsername()));
        offerteFruitore.togliRitirate();
        Offerta daScambiare=offerteFruitore.scegliOfferta();
        Offerta vorrei=tutteLeOfferte.scegliOffertaScambio(daScambiare);
        PropostaIncontro p=null;
        long timeNow= Calendar.getInstance().getTimeInMillis();
        Scambio toRet=new Scambio(daScambiare,vorrei,p,timeNow);
        return toRet;

    }

    /**
     * metodo che restituisce un int in base allo stato dello scambio
     * @return 0 se non è neanche ora stata accettata l'offerta, 1 se è stato accettata e si stanno accordando sullo scambio
     * 2 se è scaduto il tempo e allora le offerte tornano aperte, 3 se i due fruitore si sono accordati sullo scambio
     */
    public int statoScambio(){
        int stato=0;
        if(offerente.getStatoAttuale()==StatoOfferta.ACCOPIATA && ricevente.getStatoAttuale()==StatoOfferta.SELEZIONATA){
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
}
