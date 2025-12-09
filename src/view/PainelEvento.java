package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.command.Command;
import controller.command.NavegarCommand;
import view.creators.HomeCreator;
import view.factory.IViewCreator;
import interfaces.repositorioInterface.EventoRepositorio;
import servico.EventoServico;
import persistencia.EventoDAO;

public class PainelEvento extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private final EventoServico eventoServico;

    public PainelEvento() {
    	EventoRepositorio repositorio = new EventoDAO(); 
        this.eventoServico = new EventoServico(repositorio);

        setTitle("Gerenciamento de Eventos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(690, 5, 80, 20); 
        getContentPane().add(btnVoltar);
        
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IViewCreator homeCreator = new HomeCreator();
                Command navegar = new NavegarCommand(PainelEvento.this, homeCreator);
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
        
        tabbedPane.addTab("Cadastro de Evento", new PainelCadastroEvento());
        tabbedPane.addTab("Listar Evento", new PainelListarEventos(this.eventoServico));
        tabbedPane.addTab("Listar Por Mês", new PainelListarEventosPorMes(this.eventoServico));
        tabbedPane.addTab("Listar Por Dia", new PainelListarEventosPorDia(this.eventoServico));
        
        getContentPane().add(tabbedPane);
        setVisible(true);
    }
}