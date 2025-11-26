package servico;

import modelo.User;

public class SessionManager {

    private static SessionManager instance;
    private User userLogado;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if(instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void logarUsuario(User user) {
        this.userLogado = user;
    }

    public void deslogar() {
        this.userLogado = null;
    }

    public User getUsuario() {
        return this.userLogado;
    }

}
