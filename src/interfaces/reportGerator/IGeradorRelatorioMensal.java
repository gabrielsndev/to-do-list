package interfaces.reportGerator;

import modelo.Tarefa;
import java.util.List;

/**
 * Define um contrato para geradores de relatórios mensais.
 */
public interface IGeradorRelatorioMensal {
    void gerarRelatorioMensal(List<Tarefa> tarefas, int ano, int mes, String nomeArquivo);
}
