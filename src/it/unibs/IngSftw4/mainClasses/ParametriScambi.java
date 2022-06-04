package it.unibs.IngSftw4.mainClasses;


import java.util.ArrayList;

/**
 * Classe per la gestione dei parametri degli scambi
 * @author Jacopo Tedeschi,Enrico Zambelli
 */
public class ParametriScambi {
    private String piazza;
    private ArrayList<String> luoghi = new ArrayList<>();
    private ArrayList<Giorno> giorni = new ArrayList<>();
    private ArrayList<Intervallo> intervalli = new ArrayList<>();
    private int scadenza;

    /**
     * Costruttore della classe ParametriScambi
     * @param _piazza la piazza in cui avvengono gli scambi
     * @param _luoghi i luoghi della piazza in cui avvengono gli scambi
     * @param _giorni i giorni in cui avvengono gli scambi
     * @param _intervalli gli intervalli di tempo in cui avvengono gli scambi
     * @param _scadenza il numero massimo di giorni entro cui un fruitore può accettare una proposta di scambio
     */
    public ParametriScambi(String _piazza, ArrayList<String> _luoghi, ArrayList<Giorno> _giorni, ArrayList<Intervallo> _intervalli, int _scadenza) {
        this.giorni = _giorni;
        this.intervalli = _intervalli;
        this.piazza = _piazza;
        this.luoghi = _luoghi;
        this.scadenza = _scadenza;
    }
    /**
     * Metodo get per i luoghi di scambio
     * @return i luoghi di scambio
     */
    public ArrayList<String> getLuoghi() {
        return luoghi;
    }

    /**
     * Metodo get per la piazza di scambio
     * @return la piazza di scambio
     */
    public String getPiazza() {
        return piazza;
    }

    /**
     * Metodo get per i giorni di scambio
     * @return i giorni di scambio
     */
    public ArrayList<Giorno> getGiorni() {
        return giorni;
    }

    /**
     * Metodo get per gli intervalli di scambio
     * @return gli intervalli di scambio
     */
    public ArrayList<Intervallo> getIntervalli() {
        return intervalli;
    }

    /**
     * Metodo get per la scadenza di accettazione
     * @return la scadenza di accettazione
     */
    public int getScadenza() {
        return scadenza;
    }

    /**
     * Metodo che restituisce la stringa corrispondente alla descrizione dei parametri
     * @return la stringa di visualizzazione dei parametri
     */
    public String toStringParametri() {
        StringBuffer stb = new StringBuffer();
        stb.append("Piazza: " + this.piazza);
        stb.append("\nLuoghi: ");
        for (int i = 0; i < luoghi.size(); i++) {
            stb.append(luoghi.get(i));
            if (i != luoghi.size() - 1) {
                stb.append(",");
            }
        }
        stb.append(".");
        stb.append("\nGiorni: ");
        for (int i = 0; i < giorni.size(); i++) {
            stb.append(giorni.get(i).toString());
            if (i != giorni.size() - 1) {
                stb.append(",");
            }
        }
        stb.append(".");
        for (Intervallo x : this.intervalli) {
            stb.append("\nOrario: " + x.getOre()[0].toStringOrario() + "-" + x.getOre()[1].toStringOrario());
        }

        stb.append("\nScadenza: " + scadenza);
        return stb.toString();
    }

    /**
     * metodo che restituisce i giorni della settimana sotto forma di numero partendo da giovedì
     * @return lista dei numeri rispettivi ai giorni
     */
    public ArrayList<Integer> giorniInInteri(){
        ArrayList <Integer> giorniInNum=new ArrayList<>();
        for(Giorno g: this.giorni){
           giorniInNum.add(g.getNumFromDay());
        }
        return giorniInNum;
    }

    /**
     * Metodo statico per l'inserimento dei parametri del sistema per la prima volta
     * @return i parametri di scambio inseriti
     */
    public static ParametriScambi inserimentoParametri() {
        String piazza = Utilita.leggiStringaNonVuota("Inserisci il nome della città in cui avvengono gli scambi:");
        int scelta = 1;
        ArrayList<String> luoghi = new ArrayList<>();
        while (scelta != 0) {
            String luogo = Utilita.leggiStringaNonVuota("Inserisci il nome del luogo in cui vengono effettuati gli scambi:");
            luoghi.add(luogo);
            scelta = Utilita.leggiIntero("Inserisci 1 per aggiungere un altro luogo, 0 altrimenti:", Menu.ZERO, Menu.UNO);
        }
        ArrayList<Giorno> giorni = new ArrayList<>();
        scelta = 1;
        Giorno g;
        boolean giornoCorretto = true;
        int count = 0;
        while (scelta != 0) {
            do {
                String giorno = Utilita.leggiStringaNonVuota("Inserisci il nome del giorno in cui vengono effettuati gli scambi:");
                g = Giorno.getGiornoFromString(giorno);
                if (g == null) {
                    System.out.println("Il giorno inserito è inesistente");
                    giornoCorretto = false;
                }
                if (giorni.contains(g)) {
                    System.out.println("questo giorno è già stato inserito");
                    giornoCorretto = false;
                }
                if (g != null && !giorni.contains(g))
                    count++;
                giornoCorretto = true;
            } while (count == 0);
            if (giornoCorretto) {
                giorni.add(g);
            }
            scelta = Utilita.leggiIntero("Inserisci 1 per aggiungere un altro giorno, 0 altrimenti:", Menu.ZERO, Menu.UNO);
        }

        ArrayList<Intervallo> intervalli = new ArrayList<>();
        scelta = 1;
        while (scelta != 0) {
            intervalli.add(ParametriScambi.addIntervallo());
            scelta = Utilita.leggiIntero("Inserisci 1 per introdurre un nuovo intervallo, 0 altrimenti:");
        }

        int scadenza = Utilita.leggiIntero("Inserisci il numero di giorni disponibili per accettare un'offerta:");

        return new ParametriScambi(piazza, luoghi, giorni, intervalli, scadenza);

    }

    /**
     * Metodo per l'aggiunta di un luogo
     * @param s il luogo da aggiungere
     */
    public void addLuogo(String s) {
        this.luoghi.add(s);
    }

    /**
     * Metodo statico per la creazione di un intervallo inserito dall'utente
     * @return l'intervallo creato
     */
    public static Intervallo addIntervallo() {
        boolean intervalloValido = false;
        Orario inizio;
        Orario fine;
        Intervallo intervallo = null;
        do {
            Orario[] orari = new Orario[2];
            boolean finito = false;
            do {
                int ora = Utilita.leggiIntero("Inserisci l'ora dell'inizio dell'intervallo(compresa tra 0 e 24):");
                int minuti = Utilita.leggiIntero("Inserisci i minuti dell'inizio dell'intervallo(0 oppure 30):");
                inizio = new Orario(ora, minuti);
                if (inizio.orarioValido()) {
                    finito = true;
                } else {
                    System.out.println("L'orario inserito non è valido");
                }
            } while (!finito);
            finito = false;
            do {
                int ora = Utilita.leggiIntero("Inserisci l'ora della fine dell'intervallo(compresa tra 0 e 24):");
                int minuti = Utilita.leggiIntero("Inserisci i minuti della fine dell'intervallo(0 oppure 30):");
                fine = new Orario(ora, minuti);
                if (fine.orarioValido()) {
                    finito = true;
                } else {
                    System.out.println("L'orario inserito non è valido");
                }
            } while (!finito);
            orari[0] = inizio;
            orari[1] = fine;
            intervallo = Intervallo.creaIntervallo(orari);
            if (!intervallo.intervalloValido()) {
                System.out.println("L'intervallo inserito non è valido");

            } else {
                intervalloValido = true;
            }
        } while (!intervalloValido);
        return intervallo;

    }

    /**
     * Metodo che restituisce una stringa relativa agli orari
     * @return stringa relativa agli orari
     */
    public String vediIntervalli(){
        StringBuffer sb=new StringBuffer();
        int count=0;
        if(this.intervalli.size()==1){
            sb.append("L'intervallo è: \n");
        }
        else
            sb.append("Gli intervalli sono: \n");
        for(Intervallo i: this.intervalli){
            sb.append(i.toStringIntervallo()+"\t");
            count++;
            if(count%6==0);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Metodo per togliere un intervallo dalla lista
     */
    public void togliIntervallo(){
        Intervallo toRemove=ParametriScambi.addIntervallo();
        boolean presente=false;
        int countI=0;
        for(Intervallo x: this.intervalli){
            if(!presente){
                countI++;
            }
            if(x.compareIntervallo(toRemove)){
                presente=true;
            }

        }
        if (presente) {
            this.intervalli.remove(countI-1);
            if(this.intervalli.size()==0){
                System.out.println("Ci deve essere almeno un intervallo, quindi aggiungine uno");
                this.intervalli.add(ParametriScambi.addIntervallo());
            }
        }
        else
            System.out.println("l'intervallo inserito non è presente");
    }

    /**
     * Metodo per la scelta del luogo
     * @return
     */
    public String scegliLuogo(){
        int count=1;
        for(String s:this.luoghi){
            System.out.println(count+") "+s);
            count++;
        }
        int indexLuogo=Utilita.leggiIntero("Inserisci il numero corrispondente al luogo che vuoi scegliere: ",1,this.luoghi.size());
        return this.luoghi.get(indexLuogo-1);
    }


    /**
     * Metodo per aggiungere un giorno inserito dall'utente
     */
    public void addGiorno() {
        boolean giornoCorretto = true;
        Giorno g = null;
        do {
            String giorno = Utilita.leggiStringaNonVuota("Inserisci il nome del giorno in cui vengono effettuati gli scambi:");
            g = Giorno.getGiornoFromString(giorno);
            if (g == null) {
                System.out.println("Il giorno inserito è inesistente");
                giornoCorretto = false;
            }
            if (giorni.contains(g)) {
                System.out.println("questo giorno è già stato inserito");
                giornoCorretto = false;
            }
            if (g != null && !giorni.contains(g))
                giornoCorretto = true;
        } while (!giornoCorretto);
        this.giorni.add(g);
    }

    /**
     * Metodo per rimuovere un giorno inserito dall'utente
     */
    public void togliGiorno(){
        String nomeGiorno=Utilita.leggiStringaNonVuota("Inserisci il nome del giorno da rimuovere: ");
        Giorno toRemove=Giorno.getGiornoFromString(nomeGiorno);
        boolean gironoEsiste=false;
        if(toRemove!=null ){
            if(this.giorni.contains(toRemove)){
                giorni.remove(toRemove);
                gironoEsiste=true;
            }
        }
        if(!gironoEsiste)
            System.out.println("il giorno inserito non è presente o il dato inserito non è corretto");
        if(this.giorni.size()==0){
            System.out.println("deve esserci almeno un giorno quindi inserisci un giorno");
            this.addGiorno();
        }
    }

    /**
     * Metodo per aggiungere una scadenza inserita dall'utente
     */
    public void addScadenza(){
        int scad=Utilita.leggiIntero("Inserisci la nuova scadenza: ",1,999999);
        this.scadenza=scad;
    }

    /**
     * Metodo per aggiungere un luogo inserito dall'utente
     */
    public void addLuogo(){
        String luogo=Utilita.leggiStringaNonVuota("Inserisci il nuovo luogo: ");
        this.luoghi.add(luogo);
    }

    /**
     * Metodo per togliere un luogo inserito dall'utente
     */
    public void togliLuogo(){
        String luogoToRemove=Utilita.leggiStringaNonVuota("Inserisci il nome del luogo da togliere");
        if(this.luoghi.contains(luogoToRemove)){
            this.luoghi.remove(luogoToRemove);
        }
        else{
            System.out.println("Questo luogo non è tra i luoghi presenti");
        }
        if(this.luoghi.size()==0){
            String luogoMinimo=Utilita.leggiStringaNonVuota("Deve esserci sempre almeno un luogo, quindi aggiungine uno: ");
            this.luoghi.add(luogoMinimo);
        }
    }

    /**
     * Metodo per modificare i parametri del sistema a seconda della scelta dell'utente
     */
    public void modificaParametri(){
        System.out.println("Questi sono i parametri:\n"+this.toStringParametri()+"\n");
        int choice=Utilita.leggiIntero("Inserisci\n1 per modificare i luoghi\n" +
                "2 per modificare gli intervalli\n" +
                "3 per modificare i giorni\n" +
                "4 per cambiare la scadenza: ",1,4);

        switch(choice){
            case 1:
                int choiceL=Utilita.leggiIntero("Inserisci 1 se vuoi aggiungere 2 se vuoi togliere: ",1,2);
                if(choiceL==1)
                    this.addLuogo();
                else
                    this.togliLuogo();
                break;
            case 2:
                int choiceI=Utilita.leggiIntero("Inserisci 1 se vuoi aggiungere 2 se vuoi togliere: ",1,2);
                if(choiceI==1)
                    this.intervalli.add(ParametriScambi.addIntervallo());
                else
                    this.togliIntervallo();
                break;
            case 3:
                int choiceG=Utilita.leggiIntero("Inserisci 1 se vuoi aggiungere 2 se vuoi togliere: ",1,2);
                if(choiceG==1)
                    this.addGiorno();
                else
                    this.togliGiorno();
                break;
            case 4:
                this.addScadenza();
                break;
        }
    }


}

