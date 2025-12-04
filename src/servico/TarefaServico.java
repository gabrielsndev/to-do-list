package servico;
import interfaces.ICalculadorProgresso;
import modelo.Tarefa;
import interfaces.repositorioInterface.TarefaRepositorio;
import persistencia.TarefaDAO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TarefaServico implements ICalculadorProgresso {

    private final TarefaRepositorio tarefaRepositorio = new TarefaDAO();
    private final SubtarefaServico subtarefaServico = new SubtarefaServico();
    private final SessionManager userLogado;

    public TarefaServico(SessionManager u) throws Exception {
        this.userLogado= u;
    }

    public void criarTarefa(Tarefa tarefa) throws Exception {
        List<Tarefa> buscarTarefaNaData =  tarefaRepositorio.buscarDeadLine(tarefa.getDeadline());
        if (!buscarTarefaNaData.isEmpty()) {
            throw new Exception("Já existe uma tarefa marcada para essa data.");
        }
        tarefa.setUser(userLogado.getUsuario());
        tarefaRepositorio.salvar(tarefa);
        subtarefaServico.iniciarLista(tarefa.getId());
    }

    public void excluir(long id) throws Exception {
        Optional<Tarefa> optionalBuscarId =  tarefaRepositorio.buscar(id);

        if (optionalBuscarId.isPresent()) {
            Tarefa tarefa = optionalBuscarId.get();
            tarefaRepositorio.remover(tarefa.getId());
            subtarefaServico.limparSubtarefasOrfas(tarefa.getId());
            System.out.println("Tarefa removida com sucesso!");
        }else{
            throw new Exception("Tarefa não encontrada!");
        }



    }

    public void atualizarTarefa(Tarefa tarefa) throws Exception {
        List<Tarefa>  tarefaNaData = tarefaRepositorio.buscarDeadLine(tarefa.getDeadline());
        for (Tarefa existente : tarefaNaData) {
            if (!existente.getId().equals(tarefa.getId())) {
                throw new Exception("Já existe outro evento agendado para esta nova data.");
            }
        }
        tarefaRepositorio.editarTarefa(tarefa);
    }

    public List<Tarefa> listarTarefa(){
        return tarefaRepositorio.listar(this.userLogado.getUsuario().getId());
    }

    public List<Tarefa> listarTarefaCritica(List<Tarefa> tarefas) {
	    LocalDate hoje = LocalDate.now();
	    List<Tarefa> criticas = new ArrayList<>();
	    for (Tarefa t : tarefas) {
	        if (t.getDeadline() == null) {
	            continue;
	        }
	        long dias = ChronoUnit.DAYS.between(hoje, t.getDeadline());
	        if (t.getDiasCriticos() != null && dias <= t.getDiasCriticos()) {
	            criticas.add(t);
	        }
	    }
	    return criticas;
	}


    @Override
    public double calcularPercentualConcluido(Tarefa tarefa) {
        return 0;
    }
}
