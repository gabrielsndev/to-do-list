<<<<<<<< HEAD:src/relatorios/RelatorioServico.java
package relatorios;
========
package servico;
>>>>>>>> origin/main:src/servico/RelatorioServico.java

import email.Mensageiro;
import modelo.Tarefa;

import java.time.LocalDate;
import java.util.List;

public class RelatorioServico {

    public void enviarRelatorioDoDiaPorEmail(LocalDate dia, List<Tarefa> tarefas, String destinatario) {
        String nomeArquivo = "relatorio-" + dia + ".pdf";

        // 1. Gerar o PDF com nome personalizado
        GeradorDeRelatorios.gerarRelatorioPDFDoDia(dia, tarefas, nomeArquivo);

        // 2. Enviar o PDF gerado por e-mail
        Mensageiro.enviarEmail(destinatario, "Relatório de tarefas do dia: " + dia, nomeArquivo);
    }
}
