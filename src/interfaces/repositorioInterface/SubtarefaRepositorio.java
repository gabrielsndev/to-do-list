package interfaces.repositorioInterface;
import modelo.Subtarefa;
import modelo.Tarefa;

import java.util.List;
import java.util.Optional;

public interface SubtarefaRepositorio {

    void salvar(Subtarefa subtarefa);

    void editar(Subtarefa subtarefa);

    void remover(long id);

    Optional<Subtarefa> buscarPorId(long id);

    List<Subtarefa> listarTodas();

    List<Subtarefa> listarPorTarefa(Tarefa tarefa);

    int limparSubtarefasOrfas();
}

