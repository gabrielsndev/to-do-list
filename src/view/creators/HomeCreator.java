package view.creators;

import javax.swing.JFrame;

import view.Home;
import view.factory.IViewCreator;

public class HomeCreator implements IViewCreator{

	public JFrame createView() {
		Home view = new Home();
		return view;
	}
	
}
