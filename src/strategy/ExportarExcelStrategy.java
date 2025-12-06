package strategy;

import strategy.IExportacaoStrategy;
import java.util.List;
import modelo.Tarefa;
import relatorios.GeradorDeRelatorios;
import interfaces.reportGerator.IGeradorRelatorioMensal;
import interfaces.ICalculadorProgresso;

public class ExportarExcelStrategy implements IExportacaoStrategy {
    
    private int ano;
    private int mes;
    private ICalculadorProgresso calculador; 

    public ExportarExcelStrategy(int ano, int mes) {
        this.ano = ano;
        this.mes = mes;
       
    }

    @Override
    public void exportar(List<Tarefa> tarefas) {
        String nomeArquivo = "relatorio-" + ano + "-" + mes + ".xlsx";
        
        IGeradorRelatorioMensal gerador = GeradorDeRelatorios.createMonthlyGenerator("EXCEL", calculador);
        gerador.gerarRelatorioMensal(tarefas, ano, mes, nomeArquivo);
        
        System.out.println("Estratégia Excel executada.");
    }
}