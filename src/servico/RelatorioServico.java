package servico;

import java.time.LocalDate;
import java.util.List;

import email.Mensageiro;
import interfaces.reportGerator.IGeradorRelatorioDiario;
import interfaces.reportGerator.IGeradorRelatorioMensal;
import modelo.Tarefa;
import persistencia.TarefaDAO;
import relatorios.GeradorDeRelatorios;
import strategy.IExportacaoStrategy;

public class RelatorioServico {
    private TarefaServico tarefaServico;

    public RelatorioServico(SessionManager session) throws Exception {
        this.tarefaServico = new TarefaServico(session, new TarefaDAO());
    }

    
    public void processarExportacao(IExportacaoStrategy estrategia) throws Exception {
        
        List<Tarefa> tarefas = tarefaServico.listarTarefa();

        // O Serviço delega a execução para a estratégia
        estrategia.exportar(tarefas);
    }
    
    public TarefaServico getTarefaServico() {
        return this.tarefaServico;
    }
  
}