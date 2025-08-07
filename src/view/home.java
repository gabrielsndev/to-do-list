package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					home frame = new home();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} 
	
	public home() {
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
				dispose();
				TarefaPrincipal tarefa = new TarefaPrincipal();
				tarefa.setLocationRelativeTo(null);
				tarefa.setVisible(true);
				
			}
		});
		btnNewButton.setBounds(10, 125, 80, 29);
		panel.add(btnNewButton);
		
		JButton btnSubtarefa = new JButton("Exportar");
		btnSubtarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Exportar exportar = new Exportar();
				exportar.setLocationRelativeTo(null);
				exportar.setVisible(true);
			}
			
		});
		btnSubtarefa.setBounds(93, 125, 95, 29);
		panel.add(btnSubtarefa);
		
		JButton btnEvento = new JButton("Evento");
		btnEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Evento evento = new Evento();
				evento.setLocationRelativeTo(null);
				evento.setVisible(true);
			}
			
		});
		
		btnEvento.setBounds(192, 125, 80, 29);
		panel.add(btnEvento);

	}
}
