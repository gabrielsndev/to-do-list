package servico;

import modelo.Subtarefa;
import persistencia.SubtarefaDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SubtarefaServico {

    private final SubtarefaDAO dao = new SubtarefaDAO();

    public SubtarefaServico() throws Exception {
    }

    public void iniciarLista(Long id) throws Exception {
        dao.iniciarListaSubtarefas(id);
    };

    public void limparSubtarefasOrfas(Long id)throws Exception {
        dao.limparOrfas(id);
    }

}