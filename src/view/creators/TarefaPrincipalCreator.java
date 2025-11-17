package view.creators;

import javax.swing.JFrame;

import view.TarefaPrincipal;
import view.factory.IViewCreator;

public class TarefaPrincipalCreator implements IViewCreator{

	public JFrame createView() {
		TarefaPrincipal view = new TarefaPrincipal();
		return view;
	}

}
