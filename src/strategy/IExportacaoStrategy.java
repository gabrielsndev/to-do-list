package strategy;

import java.util.List;

import modelo.Tarefa;

public interface IExportacaoStrategy {
	void exportar(List<Tarefa> tarefa);
}

