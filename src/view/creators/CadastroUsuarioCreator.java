package view.creators;

import javax.swing.JFrame;

import view.CadastroUsuario;
import view.factory.IViewCreator;

public class CadastroUsuarioCreator implements IViewCreator{

	@Override
	public JFrame createView() {
		CadastroUsuario view = new CadastroUsuario();
		return view;
	}

}
