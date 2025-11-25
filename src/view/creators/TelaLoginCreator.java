package view.creators;

import javax.swing.JFrame;

import view.TelaLogin;
import view.factory.IViewCreator;

public class TelaLoginCreator implements IViewCreator{

	public JFrame createView() {
		TelaLogin view = new TelaLogin();
		return view;
	}

}
