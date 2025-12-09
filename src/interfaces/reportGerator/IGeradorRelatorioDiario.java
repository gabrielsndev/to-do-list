package interfaces.reportGerator;

import modelo.Tarefa;
import java.time.LocalDate;
import java.util.List;


public interface IGeradorRelatorioDiario {
    void gerarRelatorioDiario(List<Tarefa> tarefas, LocalDate dia, String nomeArquivo);
}
