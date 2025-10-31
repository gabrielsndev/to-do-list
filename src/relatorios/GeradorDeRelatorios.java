package relatorios;

import interfaces.reportGerator.IReportGenerator;
import servico.TarefaServico;

public class GeradorDeRelatorios {

    public static IReportGenerator createReportGenerator(String tipo, TarefaServico tarefaServico) {
        if ("PDF".equalsIgnoreCase(tipo)) {
            return new PDFGerator();
        } else if ("EXCEL".equalsIgnoreCase(tipo)) {
            return new ExcelGerator(tarefaServico);
        }
        throw new IllegalArgumentException("Tipo de relatório desconhecido: " + tipo);
    }
}
