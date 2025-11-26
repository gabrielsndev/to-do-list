package controller.command;

import javax.swing.JFrame;
import view.factory.*;

public class NavegarCommand implements Command {

    private JFrame janelaAtual;
    private IViewCreator viewCreator; 

    
    public NavegarCommand(JFrame janelaAtual, IViewCreator viewCreator) {
        this.janelaAtual = janelaAtual;
        this.viewCreator = viewCreator;
    }

    public void execute() throws Exception {
        JFrame proximaView = viewCreator.createView(); 
        
        proximaView.setLocationRelativeTo(null);
        proximaView.setVisible(true);
        janelaAtual.dispose();
    }
}