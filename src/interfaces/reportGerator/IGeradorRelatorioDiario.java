package interfaces.reportGerator;

import modelo.Tarefa;
import java.time.LocalDate;
import java.util.List;

/**
 * Define um contrato para geradores de relatórios diários.
 */
public interface IGeradorRelatorioDiario {
    void gerarRelatorioDiario(List<Tarefa> tarefas, LocalDate dia, String nomeArquivo);
}
