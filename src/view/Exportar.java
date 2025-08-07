package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

public class Exportar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldEmail;
	private JTextField textFieldDiaEspecifico;
	private JTextField textFieldMesEspecifico;

	public Exportar() {
		setTitle("Exportar");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(690, 5, 80, 20); // posição no topo direito
        getContentPane().add(btnVoltar);
        
        btnVoltar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		home home = new home();
        		home.setVisible(true);
        		home.setLocationRelativeTo(null);
        		
        	}
        });
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 784, 561);
        tabbedPane.addTab("Exportar Para o Email",painelEmail());
        tabbedPane.addTab("Exportar Para Planilha",painelPlanilha());
        
        
        getContentPane().add(tabbedPane);
        setVisible(true);
	}
	
	private JPanel painelEmail() {
		JPanel email = new JPanel();
		email.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Exportar Para O Email");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(267, 48, 234, 62);
		email.add(lblNewLabel);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(277, 144, 224, 31);
		email.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Digite o Email para receber o arquivo: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(267, 119, 234, 14);
		email.add(lblNewLabel_1);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textFieldEmail.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnEnviar, "Arquivo enviado !","Sucesso",JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(btnEnviar, "Preencha o campo de Email!","Erro",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnEnviar.setBounds(324, 186, 117, 35);
		email.add(btnEnviar);
		
		return email;
	}
	
	
	private JPanel painelPlanilha() {
		JPanel planilha = new JPanel();
		planilha.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Exportação Com Um Dia Especifico");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2.setBounds(10, 11, 350, 47);
		planilha.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Digite o Dia Especifico");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_3.setBounds(265, 69, 244, 22);
		planilha.add(lblNewLabel_3);
		
		textFieldDiaEspecifico = new JTextField();
		textFieldDiaEspecifico.setBounds(297, 102, 150, 27);
		planilha.add(textFieldDiaEspecifico);
		textFieldDiaEspecifico.setColumns(10);
		
		JButton btnDiaEspecifico = new JButton("Exportar");
		btnDiaEspecifico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textFieldDiaEspecifico.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnDiaEspecifico, "Dia Especifico EXPORTADO", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(btnDiaEspecifico, "Dia Especifico Não EXPORTADO", "Erro", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnDiaEspecifico.setBounds(307, 140, 125, 33);
		planilha.add(btnDiaEspecifico);
		
		JLabel lblNewLabel_2_1 = new JLabel("Exportação Com Um Dia Especifico");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2_1.setBounds(10, 207, 350, 47);
		planilha.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("Digite a Mes Especifico");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_3_1.setBounds(260, 275, 244, 22);
		planilha.add(lblNewLabel_3_1);
		
		textFieldMesEspecifico = new JTextField();
		textFieldMesEspecifico.setColumns(10);
		textFieldMesEspecifico.setBounds(297, 306, 150, 27);
		planilha.add(textFieldMesEspecifico);
		
		JButton btnMesEspecifico = new JButton("Exportar");
		btnMesEspecifico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textFieldMesEspecifico.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnDiaEspecifico, "Mes Especifico Exportado", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(btnDiaEspecifico, "Mes Especifico Não EXPORTADO", "Erro", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnMesEspecifico.setBounds(307, 356, 125, 33);
		planilha.add(btnMesEspecifico);
		
		return planilha;
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Exportar frame = new Exportar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
