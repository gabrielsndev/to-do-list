package modelo;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

public class TarefaServico {
	public List<Tarefa> listarTarefaCritica(List<Tarefa> tarefas) {
	    LocalDate hoje = LocalDate.now();
	    List<Tarefa> criticas = new ArrayList<>();
	    for (Tarefa t : tarefas) {
	        if (t.getDeadline() == null) {
	            // Ignora essa tarefa, porque não tem data limite
	            continue;
	        }
	        long dias = ChronoUnit.DAYS.between(hoje, t.getDeadline());
	        if (dias <= 3) {
	            criticas.add(t);
	        }
	    }
	    return criticas;
	}

}
