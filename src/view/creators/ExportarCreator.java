package view.creators;

import javax.swing.JFrame;

import view.Exportar;
import view.factory.IViewCreator;

public class ExportarCreator implements IViewCreator{

	public JFrame createView() {
		Exportar view = new Exportar();
		return view;
	}

}
