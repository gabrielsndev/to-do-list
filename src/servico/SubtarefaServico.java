package servico;

import modelo.Subtarefa;
import modelo.Tarefa;
import persistencia.SubtarefaDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SubtarefaServico {

    private final SubtarefaDAO dao = new SubtarefaDAO();

    public SubtarefaServico() throws Exception {
    }

    public void salvarNovaSubtarefa(Subtarefa subtarefa, Long idTask) throws  Exception {
        dao.salvar(subtarefa, idTask);
    }

    public void iniciarLista(Long id) throws Exception {
        dao.iniciarListaSubtarefas(id);
    };

    public void limparSubtarefasOrfas(Long id)throws Exception {
        dao.limparOrfas(id);
    }

    public List<Subtarefa> listarSubtarefas(List<Tarefa> task) {
        return dao.listarSubtarefas(task);
    }

    public void remover(String id) throws Exception {
        dao.remover(id);
    }
}