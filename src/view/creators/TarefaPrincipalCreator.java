package view.creators;

import javax.swing.JFrame;

import persistencia.TarefaDAO;
import servico.SessionManager;
import servico.TarefaServico;
import view.TarefaPrincipal;
import view.factory.IViewCreator;

public class TarefaPrincipalCreator implements IViewCreator{

	
	
		
		public JFrame createView() {
			try {
	           
	            TarefaDAO dao = new TarefaDAO();
	            SessionManager session = SessionManager.getInstance();
	            
	            TarefaServico servico = new TarefaServico(session, dao);
	            
	          
	            return new TarefaPrincipal(servico);
	            
			} catch (Exception e) {
				e.printStackTrace();
	           
				throw new RuntimeException("Erro ao criar a tela TarefaPrincipal: " + e.getMessage());
			}
		}
		
		
	

}
