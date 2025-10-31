package servico;

import email.Mensageiro;
import interfaces.reportGerator.IReportGenerator;
import modelo.Tarefa;
import relatorios.PDFGerator;

import java.time.LocalDate;
import java.util.List;

public class RelatorioServico {

    public void enviarRelatorioDoDiaPorEmail(LocalDate dia, List<Tarefa> tarefas, String destinatario) {
        String nomeArquivo = "relatorio-" + dia + ".pdf";

        // 1. Gerar o PDF com nome personalizado
        IReportGenerator gerador = new PDFGerator();
        gerador.gerarRelatorioDiario(tarefas, dia, nomeArquivo);

        // 2. Enviar o PDF gerado por e-mail
        Mensageiro.enviarEmail(destinatario, "Relatório de tarefas do dia: " + dia, nomeArquivo);
    }
}
