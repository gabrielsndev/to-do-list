package interfaces.repositorioInterface;

import modelo.Tarefa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TarefaRepositorio {

    void salvar(Tarefa t) throws  Exception;
    void editarTarefa(Tarefa t) throws Exception;
    void remover(long id) throws Exception;
    Optional<Tarefa> buscar(long id);
    List<Tarefa> listar(long id);
    List<Tarefa> buscarDeadLine(LocalDate deadline);
}
