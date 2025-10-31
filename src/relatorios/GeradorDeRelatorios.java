package relatorios;

import interfaces.ICalculadorProgresso;
import interfaces.reportGerator.IGeradorRelatorioDiario;
import interfaces.reportGerator.IGeradorRelatorioMensal;

public class GeradorDeRelatorios {

    public static IGeradorRelatorioDiario createDailyGenerator(String tipo) {
        if ("PDF".equalsIgnoreCase(tipo)) {
            return new PDFGerator();
        }
        // Futuramente, um novo tipo como "ExcelDaily" poderia ser adicionado aqui.
        throw new IllegalArgumentException("Tipo de relatório diário desconhecido ou não suportado: " + tipo);
    }

    public static IGeradorRelatorioMensal createMonthlyGenerator(String tipo, ICalculadorProgresso calculador) {
        if ("EXCEL".equalsIgnoreCase(tipo)) {
            return new ExcelGerator(calculador);
        }
        throw new IllegalArgumentException("Tipo de relatório mensal desconhecido ou não suportado: " + tipo);
    }
}
