package servico;
import modelo.Tarefa;
import interfaces.repositorioInterface.TarefaRepositorio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TarefaServico implements interfaces.ICalculadorProgresso {

    private final TarefaRepositorio tarefaRepositorio;

    public TarefaServico(TarefaRepositorio tarefaRepositorio) {
        this.tarefaRepositorio = tarefaRepositorio;
    }

    public void criarTarefa(Tarefa tarefa) throws Exception {
        List<Tarefa> buscarTarefaNaData =  tarefaRepositorio.buscarDeadLine(tarefa.getDeadline());

        if (buscarTarefaNaData.isEmpty()) {
            throw new Exception("Já existe uma tarefa marcada para essa data.");
        }
        tarefaRepositorio.salvar(tarefa);
    }

    public void excluir(long id) throws Exception {
        Optional<Tarefa> optionalBuscarId =  tarefaRepositorio.buscar(id);

        if (optionalBuscarId.isPresent()) {
            Tarefa tarefa = optionalBuscarId.get();
            tarefaRepositorio.remover(tarefa.getId());
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
        return tarefaRepositorio.listar();
    }

    public List<Tarefa> listarTarefaCritica(List<Tarefa> tarefas) {
	    LocalDate hoje = LocalDate.now();
	    List<Tarefa> criticas = new ArrayList<>();
	    for (Tarefa t : tarefas) {
	        if (t.getDeadline() == null) {
	            continue;
	        }
	        long dias = ChronoUnit.DAYS.between(hoje, t.getDeadline());
	        if (dias <= t.getDiasCriticos()) {
	            criticas.add(t);
	        }
	    }
	    return criticas;
	}

	@Override
	public double calcularPercentualConcluido(Tarefa tarefa) {
        if (tarefa.getSubtarefas() == null || tarefa.getSubtarefas().isEmpty()) {
            return 0.0;
        }
        return tarefa.getSubtarefas().stream()
                .mapToDouble(subtarefa -> subtarefa.getPercentualConcluido())
                .average()
                .orElse(0.0);
    }
}
