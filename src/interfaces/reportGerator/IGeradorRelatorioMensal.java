package interfaces.reportGerator;

import modelo.Tarefa;
import java.util.List;


public interface IGeradorRelatorioMensal {
    void gerarRelatorioMensal(List<Tarefa> tarefas, int ano, int mes, String nomeArquivo);
}
