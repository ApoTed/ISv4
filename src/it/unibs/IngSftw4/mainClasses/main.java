package it.unibs.IngSftw4.mainClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import it.unibs.IngSftw4.xmlUtilities.*;


import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class main {

    public static void main(String[] args) throws XMLStreamException, ParserConfigurationException, ParseException {
        // TODO Auto-generated method stubsss
        //long lgwegw=Calendar.getInstance().getTimeInMillis();
        //long bbeoe=lgwegw+(24*60*60*1000*2); tempo attuale + 2 giorni


        ArrayList <Utente> l=new ArrayList<Utente>();
        DatiUtenti x=new DatiUtenti(l);
        File fileUtenti = new File("listaUtenti.xml");
        if(fileUtenti.exists() && !fileUtenti.isDirectory()) {
            x=XmlReader.leggiUtenti("listaUtenti.xml");
        }
        File fileScambi=new File("scambi.xml");
        ArrayList<Scambio> ls=new ArrayList<>();
        ListaScambi scambi=new ListaScambi(ls);
        if(fileScambi.exists() && !fileScambi.isDirectory()){
           scambi=XmlReader.leggiScambi("scambi.xml");
        }
        Utente acceduto=x.menuAccesso();
        ArrayList <Gerarchia> gs=new ArrayList<>();
        Sistema sistema=new Sistema(gs);
        ParametriScambi param=null;
        File fileParametri=new File("parametriSalvati.xml");
        boolean parametriFatti=true;
        if(fileParametri.exists() && !fileParametri.isDirectory()){
            param=XmlReader.leggiParametri("parametriSalvati.xml");
        }
        else{
            parametriFatti=false;
        }
        File fileSistema = new File("sistema.xml");
        if(fileSistema.exists() && !fileSistema.isDirectory()) {
            sistema= XmlReader.readSis("sistema.xml");
        }
        if(!parametriFatti && acceduto instanceof Configuratore){
            param=ParametriScambi.inserimentoParametri();
        }
        Configurazione conf=new Configurazione(sistema,param);
        ArrayList <Offerta> listaOff=new ArrayList<>();
        File fileOfferte = new File("offerte.xml");
        if(fileOfferte.exists() && !fileOfferte.isDirectory()) {
            listaOff.addAll(XmlReader.leggiOfferte("offerte.xml").getListaOfferte());
        }
        Offerte offerte=new Offerte(listaOff);
        if(scambi!=null && offerte.getListaOfferte().size()!=0){
            if(scambi.getScambi().size()>0)
                scambi.controllaValiditaScambi(conf.getParametri(), offerte);
        }

        //PropostaIncontro p=PropostaIncontro.creaProposta(acceduto.getUsername(), conf.getParametri());
        if(acceduto instanceof Configuratore){
            String titolo="Benvenuto nel sistema di gestione baratti";
            String[] voci=new String[]{};
            Menu m=new Menu(titolo,voci);
            m.MenuConfiguratore(conf, offerte);
        }
        if(acceduto instanceof  Fruitore){
           // Scambio scambio= Scambio.creaScambio(conf,offerte, (Fruitore) acceduto);
            String titolo="Benvenuto nel sistema di gestione baratti";
            String[] voci=new String[]{};
            Menu m=new Menu(titolo,voci);
            m.MenuFruitore(conf, (Fruitore) acceduto, offerte,scambi);
        }
        XmlWriter.scriviScambi(scambi,"scambi.xml");
        System.out.println("\nFINE PROGRAMMA");
        if(offerte.getListaOfferte().size()!=0)
            XmlWriter.salvaOfferte(offerte, "offerte.xml");
        XmlWriter.salvaParametri(conf.getParametri(),"parametriSalvati.xml");
        if(conf.getSis().getListaGerarchie().size()!=0){
            XmlWriter.salvaSistema(conf.getSis(), "sistema.xml");
        }
        XmlWriter.utentiWrite(x, "listaUtenti.xml");
    }
}
