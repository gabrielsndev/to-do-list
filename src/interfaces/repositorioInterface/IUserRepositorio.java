package interfaces.repositorioInterface;

import modelo.Evento;
import modelo.User;

import java.util.List;

public interface IUserRepositorio {
    void cadastrar(User user) throws  Exception;
    User buscarPorUsername(String username) throws Exception;
}