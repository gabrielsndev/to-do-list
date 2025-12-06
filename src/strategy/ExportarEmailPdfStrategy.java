package strategy;

import strategy.IExportacaoStrategy;
import java.time.LocalDate;
import java.util.List;
import modelo.Tarefa;
import relatorios.GeradorDeRelatorios;
import interfaces.reportGerator.IGeradorRelatorioDiario;
import email.Mensageiro;

public class ExportarEmailPdfStrategy implements IExportacaoStrategy {

    private String destinatario;
    private LocalDate data;

    public ExportarEmailPdfStrategy(String destinatario, LocalDate data) {
        this.destinatario = destinatario;
        this.data = data;
    }

    @Override
    public void exportar(List<Tarefa> tarefas) {
        String nomeArquivo = "relatorio-" + data + ".pdf";
        String mensagem = "Segue o relatório.";

        IGeradorRelatorioDiario gerador = GeradorDeRelatorios.createDailyGenerator("PDF");
        gerador.gerarRelatorioDiario(tarefas, data, nomeArquivo);

        Mensageiro.enviarEmail(destinatario, mensagem, nomeArquivo);
        
        System.out.println("Estratégia Email executada.");
    }
}