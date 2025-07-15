package modelo;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

public class TarefaServico {
	 public List<Tarefa> listarTarefaCritica(List<Tarefa> listaDeTarefas) {
	        
		 	List<Tarefa> tarefaCritica = new ArrayList<>();

	        for (Tarefa t : listaDeTarefas) {
	            long diasFaltando = ChronoUnit.DAYS.between(LocalDate.now(), t.getDeadline());
	            long diferenca = diasFaltando - t.getPrioridade();

	            if (diferenca < 0) {
	                tarefaCritica.add(t);
	            }
	        }

	        return tarefaCritica;
	}

}
