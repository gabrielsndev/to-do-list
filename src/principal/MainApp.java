package principal;

import view.factory.IViewCreator;
import view.creators.CadastroUsuarioCreator;
import view.creators.HomeCreator;
import view.creators.TelaLoginCreator;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class MainApp {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	System.out.println("Começando do outro main aaa");
                    IViewCreator telaInicial = new TelaLoginCreator();

                    
                    JFrame frameInicial = telaInicial.createView(); 
                    	
                    frameInicial.setLocationRelativeTo(null);
                    frameInicial.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}