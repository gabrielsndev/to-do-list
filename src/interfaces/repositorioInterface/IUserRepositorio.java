package interfaces.repositorioInterface;

import modelo.Evento;
import modelo.User;

import java.util.List;

public interface IUserRepositorio {
    void cadastrar(User user) throws  Exception;
    boolean encontrar(String username, String password) throws Exception;
    List<User> buscarUsuario(String username, String password) throws Exception;

}