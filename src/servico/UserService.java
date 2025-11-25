package servico;

import modelo.User;
import persistencia.UserDAO;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public UserService() {
    }

    public boolean cadastrarUsuario(User u) throws Exception {
    	
    	u.setPassword(Auth.hashearSenha(u.getPassword()));
    	
    	if(userDAO.encontrar(u.getUsername(), u.getPassword())) {
            throw new Exception("Usuário já existe");
        } else {
            this.userDAO.cadastrar(u);
            return true;
        }
    }

    public User retornarUsuario(String username, String password) throws Exception {
    	String senhaHas = Auth.hashearSenha(password);
    	
        if(!userDAO.encontrar(username, senhaHas)) {
            throw new Exception("Usuário não encontrado");
        }
        return userDAO.buscarUsuario(username, senhaHas).getFirst();
    }


}
