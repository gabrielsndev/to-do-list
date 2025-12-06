package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controller.command.Command;
import controller.command.NavegarCommand;
import view.creators.HomeCreator;
import view.factory.IViewCreator;

public class Exportar extends JFrame {

	private static final long serialVersionUID = 1L;

	public Exportar() {
		setTitle("Exportar");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(690, 5, 80, 20);
        getContentPane().add(btnVoltar);
        
        btnVoltar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                // Navegação desacoplada
        		IViewCreator homeCreator = new HomeCreator();
        		Command navegar = new NavegarCommand(Exportar.this, homeCreator);
        		try {
					navegar.execute();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        
     
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 784, 561);
        
        tabbedPane.addTab("Exportar Para o Email", new PainelExportarEmail());
        tabbedPane.addTab("Exportar Para Planilha", new PainelExportarPlanilha());
        
        getContentPane().add(tabbedPane);
        setVisible(true);
	}
}