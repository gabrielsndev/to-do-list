package strategy;

import java.util.List;

import modelo.Tarefa;
import strategy.IExportacaoStrategy;
import relatorios.GeradorDeRelatorios;
import interfaces.reportGerator.IGeradorRelatorioMensal;
import interfaces.ICalculadorProgresso;

public class ExportarPlanilhaStrategy implements IExportacaoStrategy {

	private int ano;
	private int mes;
	
	private ICalculadorProgresso calculadorProgresso;

	public ExportarPlanilhaStrategy(int ano, int mes, ICalculadorProgresso calculador) {
		this.ano = ano;
		this.mes = mes;
		this.calculadorProgresso = calculador;
	}
	
	@Override
	public void exportar(List<Tarefa> tarefas) {

		String nomeArquivo = "relatorio-" + ano + "-" + mes + ".xlsx";

		IGeradorRelatorioMensal gerador = GeradorDeRelatorios.createMonthlyGenerator("EXCEL", calculadorProgresso);

		gerador.gerarRelatorioMensal(tarefas, ano, mes, nomeArquivo);
		
		System.out.println("Estratégia Excel executada: " + nomeArquivo);
	}
}