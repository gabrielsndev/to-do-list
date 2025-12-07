package view.creators;

import javax.swing.JFrame;

import persistencia.TarefaDAO;
import servico.TarefaServico;
import view.TarefaPrincipal;
import view.factory.IViewCreator;

public class TarefaPrincipalCreator implements IViewCreator{

	
	public JFrame createView() throws Exception {
		

		TarefaPrincipal view = new TarefaPrincipal();
		return view;
	}

}
