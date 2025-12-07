	package controller.command;

	import java.awt.Component;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.time.format.DateTimeParseException;
	import javax.swing.JOptionPane;

	import modelo.Tarefa;
	import servico.SessionManager;
	import servico.TarefaServico;
	import persistencia.TarefaDAO;

	public class SalvarTarefaCommand implements Command {

	    private Component parentView;
	    private final TarefaServico tarefaServico ;

	    // Dados brutos (Raw Data) vindos da tela
	    private String titulo;
	    private String dataTexto;
	    private String prioridadeTexto;
	    private String descricao;

	    private boolean sucesso = false;

	    public SalvarTarefaCommand(Component parentView, String titulo, String dataTexto, String prioridadeTexto, String descricao) throws Exception {
	        this.parentView = parentView;
	        this.titulo = titulo;
	        this.dataTexto = dataTexto;
	        this.prioridadeTexto = prioridadeTexto;
	        this.descricao = descricao;

	        SessionManager session = SessionManager.getInstance();
	        this.tarefaServico = new TarefaServico( session, new TarefaDAO());

	    }

	    @Override
	    public void execute() {
	        this.sucesso = false;

	        try {

	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	            // Tenta converter Data
	            LocalDate deadline;
	            try {

	                // String dataLimpa = dataTexto.replace("_", "").trim();
	                deadline = LocalDate.parse(this.dataTexto, formatter);
	            } catch (DateTimeParseException e) {
	                throw new IllegalArgumentException("Data inválida. Certifique-se de usar o formato DD-MM-YYYY.");
	            }

	            int prioridade;
	            try {
	                prioridade = Integer.parseInt(this.prioridadeTexto);
	            } catch (NumberFormatException e) {
	                throw new IllegalArgumentException("A prioridade deve ser um número inteiro.");
	            }
	            Tarefa t = new Tarefa(titulo, descricao, deadline, prioridade);
	            tarefaServico.criarTarefa(t);

	            JOptionPane.showMessageDialog(parentView, "Tarefa salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	            this.sucesso = true;

	        } catch (IllegalArgumentException e) {
	            // erros de validação (Data passada, formato errado, campos vazios)
	            JOptionPane.showMessageDialog(parentView, e.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);

	        } catch (Exception e) {

	            e.printStackTrace();
	            String msg = e.getMessage();

	            JOptionPane.showMessageDialog(parentView, "Erro ao salvar: " + msg, "Erro", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    // Método para a View saber se deve limpar os campos e notificar o Mediator
	    public boolean isSucesso() {
	        return sucesso;
	    }
	}
package controller.command;

import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;

import modelo.Tarefa;
import servico.SessionManager;
import servico.TarefaServico;

public class SalvarTarefaCommand implements Command {

    private Component parentView; // Para centralizar o JOptionPane
    private final TarefaServico tarefaServico = new TarefaServico(SessionManager.getInstance());

    // Dados brutos (Raw Data) vindos da tela
    private String titulo;
    private String dataTexto;
    private String prioridadeTexto;
    private String descricao;

    private boolean sucesso = false;

    // Construtor recebe STRINGS puras, não JTextFields (desacoplamento da UI)
    public SalvarTarefaCommand(Component parentView, String titulo, String dataTexto, String prioridadeTexto, String descricao) throws Exception {
        this.parentView = parentView;
        this.titulo = titulo;
        this.dataTexto = dataTexto;
        this.prioridadeTexto = prioridadeTexto;
        this.descricao = descricao;

        // Instancia o serviço (idealmente seria injetado, mas aqui instanciamos para simplificar o fluxo)
    }

    @Override
    public void execute() {
        this.sucesso = false; // Reseta estado

        try {
            // 1. Conversão (Parsing) - Trabalho "sujo" fica no Command
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // Tenta converter Data
            LocalDate deadline;
            try {
                // Remove caracteres de máscara se necessário
                // String dataLimpa = dataTexto.replace("_", "").trim();
                deadline = LocalDate.parse(this.dataTexto, formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Data inválida. Certifique-se de usar o formato DD-MM-YYYY.");
            }

            // Tenta converter Prioridade
            int prioridade;
            try {
                prioridade = Integer.parseInt(this.prioridadeTexto);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("A prioridade deve ser um número inteiro.");
            }

            // 2. Chama o Serviço (Business Logic)
            // Passamos dados LIMPOS e TIPADOS
            Tarefa t = new Tarefa(titulo, descricao, deadline, prioridade);
            tarefaServico.criarTarefa(t);

            // 3. Feedback de Sucesso
            JOptionPane.showMessageDialog(parentView, "Tarefa salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.sucesso = true; // Marca flag para a View limpar os campos

        } catch (IllegalArgumentException e) {
            // Erros de validação (Data passada, formato errado, campos vazios)
            JOptionPane.showMessageDialog(parentView, e.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);

        } catch (Exception e) {
            // Erros técnicos ou de Regra de Negócio genérica (Colisão de data, Banco fora do ar)
            e.printStackTrace();
            String msg = e.getMessage();
            // Se a mensagem for técnica demais, você pode filtrar aqui, mas geralmente exibir o getMessage ajuda
            JOptionPane.showMessageDialog(parentView, "Erro ao salvar: " + msg, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para a View saber se deve limpar os campos e notificar o Mediator
    public boolean isSucesso() {
        return sucesso;
    }
}