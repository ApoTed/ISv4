package it.unibs.IngSftw4.mainClasses;


import java.util.ArrayList;

/**
 * Classe per la gestione degli utenti del sistema
 * @author Jacopo Tedeschi, Enrico Zambelli
 */
public class DatiUtenti {
    public static final String CATEGORIA_NON_PRESENTE = "Categoria non presente";
    public static final String INSERISCI_NOME = "Inserisci il tuo nome: ";
    public static final String INSERISCI_PASSWORD = "Inserisci la tua password: ";
    public static final Utente CREDENZIALI_PREDEFINITE = new Configuratore("UserStandard", "PasswordStandard");
    public static final String NOME_NON_DISPONIBILE = "Questo nome utente non è disponibile";
    public static final String NUOVA_PASSWORD = "Inserisci la tua nuova password";
    private ArrayList<Utente> listaUtenti = new ArrayList<Utente>();


    /**
     * Costruttore della classe DatiUtenti
     *
     * @param _listaUtenti lista degli utenti del programma
     */
    public DatiUtenti(ArrayList<Utente> _listaUtenti) {
        this.listaUtenti = _listaUtenti;

    }

    /**
     * Metodo per la gestione dell'accesso
     * @return l'utente che ha eseguito correttamente l'accesso, null se l'accesso è fallito
     */
    public Utente menuAccesso() {
        boolean successo=false;
        //chiedi se è un nuovo fruitore
        //fruitore inserisci le tue nove credenziali
        int scelta=Utilita.leggiIntero("Inserisci 1 se sei un nuovo fruitore e vuoi registrarti, 0 altrimenti: ",0,1);
        String username=null;
        String password=null;
        if(scelta==1){
            do{
                username=Utilita.leggiStringaNonVuota("Inserisci il nome con cui vuoi registrarti");
                if(this.checkName(username)){
                    System.out.println(NOME_NON_DISPONIBILE);
                }
            }while(this.checkName(username));
            password=Utilita.leggiStringaNonVuota(NUOVA_PASSWORD);
            this.addUtente(username,password,false);
        }
        else{
            username=Utilita.leggiStringaNonVuota("Benvenuto "+ INSERISCI_NOME);
            password=Utilita.leggiStringaNonVuota(INSERISCI_PASSWORD);
        }
        Utente temp= new Utente(username, password);
        if( this.checkConf(temp)) {
            temp = nuovoConfiguratore();

        }
        for (int i=0;i<3;i++) {
            //accesso da utente già registrato 3 tentativi
            int tentativi = 2 - i;
            if(tentativi<2){
                String nameTry=Utilita.leggiStringaNonVuota(INSERISCI_NOME);
                String passwordTry=Utilita.leggiStringaNonVuota(INSERISCI_PASSWORD);
                temp=new Utente(nameTry, passwordTry);
                if( this.checkConf(temp)) {
                    temp = nuovoConfiguratore();

                }

            }
            for (Utente toCompare : this.getListaUtenti()) {
                if( Utente.sameUtente(toCompare, temp)) {
                    successo=true;
                }

            }
            if(successo==false) {

                System.out.println("Le credenziali inserite non sono corrette, hai " + tentativi + " tentativi");
            }
            if(successo==true)
                break;

        }
        if(successo==false) {
            System.out.println("Accesso fallito chiudere il programma");
            temp=null;
        }
        else {
            System.out.println("Accesso eseguito con successo");
            temp=this.getUtenteDaCredenziali(temp.getUsername(), temp.getPassword());

        }
        return temp;

    }

    /**
     * Metodo per il controllo delle credenziali di un utente
     *
     * @param userName username dell'utente
     * @param password password dell'utente
     * @return true se le credenziali sono corrette, false altrimenti
     */
    public boolean checkCredenziali(String userName, String password) {
        boolean presente = false;

        Utente temp = new Utente(userName, password);
        for (Utente x : this.listaUtenti) {
            if (Utente.sameUtente(x, temp))
                presente = true;
        }

        return presente;
    }

    /**
     * Metodo che restituisce un nuovo configuratore che si registra al sistema
     * @return il nuovo configuratore con credenziali inserite da tastiera
     */
    public Utente nuovoConfiguratore() {
        Utente temp;
        String newUsername;
        do {
            newUsername=Utilita.leggiStringaNonVuota("Inserisci il tuo nuovo nome utente:");
            if(this.checkName(newUsername)==true)
                System.out.println("Questo nome utente non è disponibile");
        }while(this.checkName(newUsername)==true);

        String newPassword=Utilita.leggiStringaNonVuota("Inserisci la tua nuova password:");
        this.addUtente(newUsername, newPassword, true);
        temp=new Configuratore(newUsername, newPassword);
        return temp;
    }

    /**
     * Metodo per il controllo delle credenziali predefinite
     *
     * @param u utente del quale si vuole verificare l'inseriemnto delle credenziali predefinite
     * @return true se le credenziali predefinite sono corrette,false altrimenti
     */
    public boolean checkConf(Utente u) {
        boolean corretto = false;
        if (Utente.sameUtente(u, CREDENZIALI_PREDEFINITE))
            corretto = true;
        return corretto;
    }

    /**
     * Metodo per verificare se uno username è già usato da un altro utente
     *
     * @param name lo username di cui si vuole verificare la presenza
     * @return true se è già presente false altrimenti
     */
    public boolean checkName(String name) {
        boolean presente = false;
        if (CREDENZIALI_PREDEFINITE.getUsername().equals(name)) {
            presente = true;
            return presente;
        }
        for (Utente x : this.listaUtenti) {
            if (x.getUsername().equals(name)) {
                presente = true;
            }

        }
        return presente;
    }

    /**
     * Metodo per làaggiunta di un configuratore alla lista degli utenti
     *
     * @param name     username dell'utente
     * @param password password dell'utente
     * @param conf     boolean che indica se l'utente è un configuratore o meno
     * @return
     */
    public boolean addUtente(String name, String password, boolean conf) {
        boolean successo = false;
        if (conf == true) {
            Configuratore c = new Configuratore(name, password);
            this.listaUtenti.add(c);
        } else {
            Fruitore f = new Fruitore(name, password);
            this.listaUtenti.add(f);
        }
        return successo;
    }

    /**
     * Metodo get per la lista degli utenti
     *
     * @return la lista degli utenti
     */
    public ArrayList<Utente> getListaUtenti() {
        return listaUtenti;
    }

    /**
     * Metodo set per la lista degli utenti
     *
     * @param listaUtenti la lista da settare
     */
    public void setListaUtenti(ArrayList<Utente> listaUtenti) {
        this.listaUtenti = listaUtenti;
    }

    /**
     * Metodo per ottenere un utente dalle sue credenziali
     *
     * @param name     lo username dell'utente da cercare
     * @param password la password dell'utente da cercare
     * @return l'utente che ha per credenziali quelle inserite
     */
    public Utente getUtenteDaCredenziali(String name, String password) {
        for (Utente x : this.listaUtenti) {
            if (x.getUsername().equals(name) && x.getPassword().equals(password)) {
                return x;
            }
        }
        return null;
    }
}


