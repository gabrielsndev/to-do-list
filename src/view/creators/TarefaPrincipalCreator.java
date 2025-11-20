package view.creators;

import javax.swing.JFrame;

import persistencia.TarefaDAO;
import servico.TarefaServico;
import view.TarefaPrincipal;
import view.factory.IViewCreator;

public class TarefaPrincipalCreator implements IViewCreator{

	
	public JFrame createView() {
		
		TarefaDAO dao = new TarefaDAO();
		TarefaServico servico = new TarefaServico(dao);
		
		TarefaPrincipal view = new TarefaPrincipal(dao, servico);
		return view;
	}

}
