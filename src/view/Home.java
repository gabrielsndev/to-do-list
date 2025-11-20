package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.command.Command;
import controller.command.NavegarCommand;
import view.factory.IViewCreator;
import view.creators.ExportarCreator;
import view.creators.PainelEventoCreator;
import view.creators.TarefaPrincipalCreator;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public Home() {
		setTitle("home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(75, 11, 281, 220);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("To Do List");
		lblNewLabel.setBounds(93, 50, 95, 18);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Bem Vindo !");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(107, 66, 65, 14);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Tarefa");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				IViewCreator creator = new TarefaPrincipalCreator();
				
				Command navegar = new NavegarCommand(Home.this, creator);
				
				navegar.execute();
			}
		});
		btnNewButton.setBounds(10, 125, 80, 29);
		panel.add(btnNewButton);
		
		JButton btnSubtarefa = new JButton("Exportar");
		btnSubtarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				IViewCreator creator = new ExportarCreator();
				
				Command navegar = new NavegarCommand(Home.this, creator);
				
				navegar.execute();
			}
		});
		btnSubtarefa.setBounds(93, 125, 95, 29);
		panel.add(btnSubtarefa);
		
		JButton btnEvento = new JButton("Evento");
		btnEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				IViewCreator creator = new PainelEventoCreator();
				
				Command navegar = new NavegarCommand(Home.this, creator);
				
				navegar.execute();
			}
		});
		
		btnEvento.setBounds(192, 125, 80, 29);
		panel.add(btnEvento);
	}
}