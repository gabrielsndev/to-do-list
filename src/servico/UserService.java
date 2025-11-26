package servico;

import modelo.User;
import persistencia.UserDAO;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public UserService() {
    }

    public boolean cadastrarUsuario(User u) throws Exception {
    	
    	User usuarioExistente = userDAO.buscarPorUsername(u.getUsername());
    	
    	if(usuarioExistente != null) {
            throw new Exception("Usuário já existe");
        } else {
        	u.setPassword(Auth.hashearSenha(u.getPassword()));
            this.userDAO.cadastrar(u);
            return true;
        }
    }

    public User retornarUsuario(String username, String password) throws Exception {
    	User usuarioEncontrado = userDAO.buscarPorUsername(username);
    	
        if(usuarioEncontrado == null) {
            throw new Exception("Usuário não encontrado");
        }
        
        
        if(!Auth.compararSenhas(password	,usuarioEncontrado.getPassword())) {
        	throw new Exception("Senha incorreta");
        }
        
        return usuarioEncontrado;
    }


}
