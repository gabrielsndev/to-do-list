package view.creators;

import javax.swing.JFrame;

import view.PainelEvento;
import view.factory.IViewCreator;

public class PainelEventoCreator implements IViewCreator{

	
	public JFrame createView() {
		PainelEvento view = new PainelEvento();
		return view;
	}
	
}
