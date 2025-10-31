package servico;

import email.Mensageiro;
import interfaces.reportGerator.IGeradorRelatorioDiario;
import modelo.Tarefa;
import relatorios.GeradorDeRelatorios;

import java.time.LocalDate;
import java.util.List;

public class RelatorioServico {

    public void enviarRelatorioDoDiaPorEmail(LocalDate dia, List<Tarefa> tarefas, String destinatario) {
        String nomeArquivo = "relatorio-" + dia + ".pdf";
        String titulo = "Relatório de tarefas do dia: " + dia;

        // 1. Gerar o PDF com nome personalizado usando a fábrica e interface corretas
        IGeradorRelatorioDiario gerador = GeradorDeRelatorios.createDailyGenerator("PDF");
        gerador.gerarRelatorioDiario(tarefas, dia, nomeArquivo);

        // 2. Enviar o PDF gerado por e-mail
        Mensageiro.enviarEmail(destinatario, titulo, nomeArquivo);
    }
}
