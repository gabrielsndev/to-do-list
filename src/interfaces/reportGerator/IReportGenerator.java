package interfaces.reportGerator;

import modelo.Tarefa;

import java.time.LocalDate;
import java.util.List;

public interface IReportGenerator {
    void gerarRelatorioDiario(List<Tarefa> tarefas, LocalDate dia, String nomeArquivo);
    void gerarRelatorioMensal(List<Tarefa> tarefas, int ano, int mes, String nomeArquivo);
}
