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