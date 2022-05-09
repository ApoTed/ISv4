package it.unibs.IngSftw4.mainClasses;


/**
 * Classe per la gestione di un utente
 *  @author Jacopo Tedeschi, Enrico Zambelli
 */
public class Utente {

    private String username;
    private String password;

    /**
     * Costruttore della classe utente
     * @param _username il nome dell'utente
     * @param _password la password dell'utente
     */
    public Utente(String _username, String _password) {
        this.setUsername(_username);
        this.setPassword(_password);
    }

    /**
     * Metodo che verifica se due utenti hanno lo stesso nome utente o la stessa password
     * @param u1 primo utente del confronto
     * @param u2 secondo utente del confronto
     * @return
     */
    public static boolean sameUtente(Utente u1, Utente u2) {
        boolean equal=false;
        if(u1.username.equals(u2.username) && u1.password.equals(u2.password)) {
            equal=true;
        }
        return equal;
    }

    /**
     * Metodo get per lo username di un utente
     * @return lo username dell'utente
     */
    public String getUsername() {
        return username;
    }

    /**
     * Metodo set per lo username di un utente
     * @param username lo username da settare
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Metodo get per la password di un utente
     * @return la password dell'utente
     */
    public String getPassword() {
        return password;
    }
    /**
     * Metodo set per la password di un utente
     * @param password la password da settare
     */
    public void setPassword(String password) {
        this.password = password;
    }


}


