package it.unibs.IngSftw4.xmlUtilities;


import  it.unibs.IngSftw4.mainClasses.ParametriScambi;
import it.unibs.IngSftw4.mainClasses.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
/**
 * classe per la gestione del salvataggio su file xml del sistema
 * @author  Enrico Zambello, Jacopo Tedeschi
 */
public class XmlWriter {
    /**
     * metodo per salvare i dati su file xml all'inteno del package
     * @param s sistema di cui si salavano i dati
     */
    public static void salvaSistema(Sistema s, String filename){
        try{
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            //sistema
            Element sistema= document.createElement("insiemeGerarchie");
            document.appendChild(sistema);
            int countGer=0;
            for(Gerarchia g: s.getListaGerarchie()){
                //gerarchia
                Element gerarchia =document.createElement("gerarchia");
                // Attr numberGerarchia= document.createAttribute("id");
                //numberGerarchia.setValue(""+countGer);
                sistema.appendChild(gerarchia);

                ArrayList <Categoria> allCat=new ArrayList<>();
                int countCat=0;
                for(Categoria x:g.getRamo().keySet()){
                    //categoria
                    Element categoria=document.createElement("categoria");
                    // Attr numberCategoria=document.createAttribute("id");
                    //numberCategoria.setValue(""+countCat);
                    gerarchia.appendChild(categoria);
                    //nome categoria
                    Element nomeCategoria=document.createElement("nomeCategoria");
                    categoria.appendChild(nomeCategoria);
                    nomeCategoria.appendChild(document.createTextNode(x.getNome()));
                    //descrizione categoria
                    Element descrizione =document.createElement("descrizione");
                    categoria.appendChild(descrizione);
                    descrizione.appendChild(document.createTextNode(x.getDescrizione()));

                    //campi nativi
                    Element campiNativi=document.createElement("campiNativi");
                    categoria.appendChild(campiNativi);
                    for(CampoNativo c:x.getCampiNativi()){
                        //campoNativo
                        Element campoNativo=document.createElement("campoNativo");
                        //Attr numberCampo=document.createAttribute("id");
                        //numberCategoria.setValue(""+countCampo);
                        campiNativi.appendChild(campoNativo);

                        //nome campo nativo
                        Element nomeCampo=document.createElement("nomeCampo");
                        campoNativo.appendChild(nomeCampo);
                        nomeCampo.appendChild(document.createTextNode(c.getNomeCampo()));

                        //obbligo descrzione campo
                        Element obbligoCampo=document.createElement("obbligoCampo");
                        campoNativo.appendChild(obbligoCampo);
                        if(c.isObbligatoria()){
                            obbligoCampo.appendChild(document.createTextNode("true"));
                        }
                        else {
                            obbligoCampo.appendChild(document.createTextNode("false"));
                        }
                    }
                    //padre categoria
                    Element padre=document.createElement("categoriaPadre");
                    categoria.appendChild(padre);
                    padre.appendChild(document.createTextNode(g.getRamo().get(x).getNome()));
                    countCat++;

                }

                countGer++;

            }
            Transformer transformer2 = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(filename));
            Source input = new DOMSource(document);
            transformer2.transform(input, output);

            //trasforma il DOM Object in un file xml da salvare in un percorso valido
            //TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //Transformer transformer = transformerFactory.newTransformer();
            //DOMSource domSource = new DOMSource(document);
            //StreamResult streamResult = new StreamResult(new File(xmlFilePath)); //xmleFilePath percoso valido dove salvare nel pc

        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }

    public static void utentiWrite(DatiUtenti utenti, String filename) throws ParserConfigurationException {
        try{
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element datiUtenti= document.createElement("datiUtenti");
            document.appendChild(datiUtenti);
            for(Utente x: utenti.getListaUtenti()){
                Element utente= document.createElement("utente");
                datiUtenti.appendChild(utente);

                //username
                Element username=document.createElement("username");
                username.appendChild(document.createTextNode(x.getUsername()));
                utente.appendChild(username);


                //password
                Element password= document.createElement("password");
                password.appendChild(document.createTextNode(x.getPassword()));
                utente.appendChild(password);

                //configuratore o fruitore
                Element tipoUtente= document.createElement("tipoUtente");
                if(x instanceof Configuratore){
                    tipoUtente.appendChild(document.createTextNode("configuratore"));
                }
                else{
                    tipoUtente.appendChild(document.createTextNode("fruitore"));
                }
                utente.appendChild(tipoUtente);

            }
            Transformer transformer2 = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(filename));
            Source input = new DOMSource(document);
            transformer2.transform(input, output);
        }catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * metodo per il salvataggio xml dei parametri
     * @param p parametri da salvare
     * @param filename nom file xml su cui salvare
     */
    public static void salvaParametri(ParametriScambi p, String filename){
        try{
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element parametri=document.createElement("ParametriScambi");
            document.appendChild(parametri);

            Element piazza=document.createElement("piazza");
            piazza.appendChild(document.createTextNode(p.getPiazza()));
            parametri.appendChild(piazza);

            Element luoghi=document.createElement("luoghi");
            parametri.appendChild(luoghi);

            for(String l: p.getLuoghi()){
                Element luogo=document.createElement("luogo");
                luogo.appendChild(document.createTextNode(l));
                luoghi.appendChild(luogo);
            }

            Element giorni= document.createElement("giorni");
            parametri.appendChild(giorni);

            for(Giorno g:p.getGiorni()){
                Element giorno=document.createElement("giorno");
                giorno.appendChild(document.createTextNode(g.toString()));
                giorni.appendChild(giorno);
            }

            Element intervalli=document.createElement("intervalli");
            parametri.appendChild(intervalli);
            for(Intervallo x: p.getIntervalli()){
                Element intervallo=document.createElement("intervallo");
                intervalli.appendChild(intervallo);

                Element orarioIniziale=document.createElement("orarioIniziale");
                orarioIniziale.appendChild(document.createTextNode(x.getOre()[0].toStringOrario()));
                intervallo.appendChild(orarioIniziale);
                Element orarioFinale=document.createElement("orarioFinale");
                orarioFinale.appendChild(document.createTextNode(x.getOre()[1].toStringOrario()));
                intervallo.appendChild(orarioFinale);
            }



            Element scadenza=document.createElement("scadenza");
            scadenza.appendChild(document.createTextNode(Integer.toString(p.getScadenza())));
            parametri.appendChild(scadenza);

            Transformer transformer2 = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(filename));
            Source input = new DOMSource(document);
            transformer2.transform(input, output);

        }catch(ParserConfigurationException | TransformerConfigurationException e){

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * metodo per salvare le offerte in un file xml
     * @param offerte offerte da salvare
     * @param filename nome del file xml in cui salvare
     */
    public static void salvaOfferte(Offerte offerte, String filename){
        try{
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element listaOfferte=document.createElement("offerte");
            document.appendChild(listaOfferte);

            for(Offerta x : offerte.getListaOfferte() ){
                Element offerta=document.createElement("offerta");
                listaOfferte.appendChild(offerta);

                Element nomeRadice=document.createElement("nomeRadice");
                offerta.appendChild(nomeRadice);
                nomeRadice.appendChild(document.createTextNode(x.getNomeRadice()));

                Element nomeCategoria=document.createElement("nomeCategoria");
                offerta.appendChild(nomeCategoria);
                nomeCategoria.appendChild(document.createTextNode(x.getNomeCategoria()));

                Element statoAttuale= document.createElement("statoAttuale");
                offerta.appendChild(statoAttuale);
                statoAttuale.appendChild(document.createTextNode(x.getStatoAttuale().toStringStato()));

                Element nomeFruitore=document.createElement("nomeFruitore");
                offerta.appendChild(nomeFruitore);
                nomeFruitore.appendChild(document.createTextNode(x.getNomeFruitore()));

                Element compilazioni=document.createElement("compilazioni");
                offerta.appendChild(compilazioni);

                for(CampoNativo c: x.getCompliazioni().keySet()){
                    Element compilazione = document.createElement("compilazione");
                    compilazioni.appendChild(compilazione);
                    //campoNativo
                    Element campoNativo=document.createElement("campoNativo");
                    compilazione.appendChild(campoNativo);

                    //nome campo nativo
                    Element nomeCampo=document.createElement("nomeCampo");
                    campoNativo.appendChild(nomeCampo);
                    nomeCampo.appendChild(document.createTextNode(c.getNomeCampo()));

                    //obbligo descrzione campo
                    Element obbligoCampo=document.createElement("obbligoCampo");
                    campoNativo.appendChild(obbligoCampo);
                    if(c.isObbligatoria()){
                        obbligoCampo.appendChild(document.createTextNode("true"));
                    }
                    else {
                        obbligoCampo.appendChild(document.createTextNode("false"));
                    }
                    Element compilazioneInserita=document.createElement("compilazioneInserita");
                    compilazione.appendChild(compilazioneInserita);
                    compilazioneInserita.appendChild(document.createTextNode(x.getCompliazioni().get(c)));

                }
                Element statiPassati=document.createElement("statiPassati");
                offerta.appendChild(statiPassati);

                for(StatoOfferta s:x.getStatiPassati()){
                    Element st=document.createElement("statoPassato");
                    statiPassati.appendChild(st);
                    st.appendChild(document.createTextNode(s.toStringStato()));
                }

            }
            Transformer transformer2 = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(filename));
            Source input = new DOMSource(document);
            transformer2.transform(input, output);
        }catch(TransformerConfigurationException | ParserConfigurationException e){

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void scriviScambi(ListaScambi listaScambi,String filename)  {
        try{
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element scambi=document.createElement("scambi");
            document.appendChild(scambi);

           for(Scambio sca:listaScambi.getScambi()){
               //offerta offerente
               Element scambio=document.createElement("scambio");
               scambi.appendChild(scambio);

               Offerta offerente=sca.getOfferente();
               Element offerta=document.createElement("offertaOfferente");
               scambio.appendChild(offerta);

               Element nomeRadice=document.createElement("nomeRadice");
               offerta.appendChild(nomeRadice);
               nomeRadice.appendChild(document.createTextNode(offerente.getNomeRadice()));

               Element nomeCategoria=document.createElement("nomeCategoria");
               offerta.appendChild(nomeCategoria);
               nomeCategoria.appendChild(document.createTextNode(offerente.getNomeCategoria()));

               Element statoAttuale= document.createElement("statoAttuale");
               offerta.appendChild(statoAttuale);
               statoAttuale.appendChild(document.createTextNode(offerente.getStatoAttuale().toStringStato()));

               Element nomeFruitore=document.createElement("nomeFruitore");
               offerta.appendChild(nomeFruitore);
               nomeFruitore.appendChild(document.createTextNode(offerente.getNomeFruitore()));

               Element compilazioni=document.createElement("compilazioni");
               offerta.appendChild(compilazioni);

               for(CampoNativo c: offerente.getCompliazioni().keySet()){
                   Element compilazione = document.createElement("compilazione");
                   compilazioni.appendChild(compilazione);
                   //campoNativo
                   Element campoNativo=document.createElement("campoNativo");
                   compilazione.appendChild(campoNativo);

                   //nome campo nativo
                   Element nomeCampo=document.createElement("nomeCampo");
                   campoNativo.appendChild(nomeCampo);
                   nomeCampo.appendChild(document.createTextNode(c.getNomeCampo()));

                   //obbligo descrzione campo
                   Element obbligoCampo=document.createElement("obbligoCampo");
                   campoNativo.appendChild(obbligoCampo);
                   if(c.isObbligatoria()){
                       obbligoCampo.appendChild(document.createTextNode("true"));
                   }
                   else {
                       obbligoCampo.appendChild(document.createTextNode("false"));
                   }
                   Element compilazioneInserita=document.createElement("compilazioneInserita");
                   compilazione.appendChild(compilazioneInserita);
                   compilazioneInserita.appendChild(document.createTextNode(offerente.getCompliazioni().get(c)));

               }
               Element statiPassati=document.createElement("statiPassati");
               offerta.appendChild(statiPassati);

               for(StatoOfferta s:offerente.getStatiPassati()){
                   Element st=document.createElement("statoPassato");
                   statiPassati.appendChild(st);
                   st.appendChild(document.createTextNode(s.toStringStato()));
               }
                //offerta ricevente
               Offerta ricevente=sca.getRicevente();
               Element offertaRicevente=document.createElement("offertaRicevente");
               scambio.appendChild(offertaRicevente);

               Element nomeRadiceRicevente=document.createElement("nomeRadice");
               offertaRicevente.appendChild(nomeRadiceRicevente);
               nomeRadiceRicevente.appendChild(document.createTextNode(ricevente.getNomeRadice()));

               Element nomeCategoriaRicevente=document.createElement("nomeCategoria");
               offertaRicevente.appendChild(nomeCategoriaRicevente);
               nomeCategoriaRicevente.appendChild(document.createTextNode(ricevente.getNomeCategoria()));

               Element statoAttualeRicevente= document.createElement("statoAttuale");
               offertaRicevente.appendChild(statoAttualeRicevente);
               statoAttualeRicevente.appendChild(document.createTextNode(ricevente.getStatoAttuale().toStringStato()));

               Element nomeFruitoreRicevente=document.createElement("nomeFruitore");
               offertaRicevente.appendChild(nomeFruitoreRicevente);
               nomeFruitoreRicevente.appendChild(document.createTextNode(ricevente.getNomeFruitore()));

               Element compilazioniRicevente=document.createElement("compilazioni");
               offertaRicevente.appendChild(compilazioniRicevente);

               for(CampoNativo c: ricevente.getCompliazioni().keySet()){
                   Element compilazione = document.createElement("compilazione");
                   compilazioniRicevente.appendChild(compilazione);
                   //campoNativo
                   Element campoNativo=document.createElement("campoNativo");
                   compilazione.appendChild(campoNativo);

                   //nome campo nativo
                   Element nomeCampo=document.createElement("nomeCampo");
                   campoNativo.appendChild(nomeCampo);
                   nomeCampo.appendChild(document.createTextNode(c.getNomeCampo()));

                   //obbligo descrzione campo
                   Element obbligoCampo=document.createElement("obbligoCampo");
                   campoNativo.appendChild(obbligoCampo);
                   if(c.isObbligatoria()){
                       obbligoCampo.appendChild(document.createTextNode("true"));
                   }
                   else {
                       obbligoCampo.appendChild(document.createTextNode("false"));
                   }
                   Element compilazioneInserita=document.createElement("compilazioneInserita");
                   compilazione.appendChild(compilazioneInserita);
                   compilazioneInserita.appendChild(document.createTextNode(ricevente.getCompliazioni().get(c)));

               }
               Element statiPassatiRicevente=document.createElement("statiPassati");
               offertaRicevente.appendChild(statiPassatiRicevente);

               for(StatoOfferta s:ricevente.getStatiPassati()){
                   Element st=document.createElement("statoPassato");
                   statiPassatiRicevente.appendChild(st);
                   st.appendChild(document.createTextNode(s.toStringStato()));
               }
               Element proposta=document.createElement("proposta");
               scambio.appendChild(proposta);
               if(sca.getUltimaProposta()!=null){

                   Element luogo=document.createElement("luogo");
                   proposta.appendChild(luogo);
                   if(sca.getUltimaProposta()==null)
                       luogo.appendChild(document.createTextNode(""));
                   else{
                       luogo.appendChild(document.createTextNode(sca.getUltimaProposta().getLuogo()));
                   }

                   Element data=document.createElement("data");
                   proposta.appendChild(data);
                   if(sca.getUltimaProposta()==null)
                       data.appendChild(document.createTextNode(""));
                   else
                       data.appendChild(document.createTextNode(sca.getUltimaProposta().getData()));

                   Element nomeF=document.createElement("nomeF");
                   if(sca.getUltimaProposta()==null)
                       nomeF.appendChild(document.createTextNode(""));
                   else
                       nomeF.appendChild(document.createTextNode(sca.getUltimaProposta().getNomeFruitore()));
                   proposta.appendChild(nomeF);


                   Element tempoProposta=document.createElement("tempoProposta");
                   proposta.appendChild(tempoProposta);
                   if(sca.getUltimaProposta()==null)
                       tempoProposta.appendChild(document.createTextNode(""));
                   else
                       tempoProposta.appendChild(document.createTextNode(Long.toString(sca.getUltimaProposta().getTempo())));

                   Element oraProposta=document.createElement("ora");
                   proposta.appendChild(oraProposta);
                   if(sca.getUltimaProposta()==null)
                       oraProposta.appendChild(document.createTextNode(""));
                   else
                       oraProposta.appendChild(document.createTextNode(sca.getUltimaProposta().getOra().toStringOrario()));
               }


               Element timeIstant=document.createElement("time");
               scambio.appendChild(timeIstant);
               timeIstant.appendChild(document.createTextNode(Long.toString(sca.getTempo())));
           }

            Transformer transformer2 = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(filename));
            Source input = new DOMSource(document);
            transformer2.transform(input, output);
        } catch (TransformerConfigurationException transformerConfigurationException) {
            transformerConfigurationException.printStackTrace();
        } catch (ParserConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        } catch (TransformerException transformerException) {
            transformerException.printStackTrace();
        }
        }

}
