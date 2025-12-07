package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import email.Mensageiro;
import interfaces.reportGerator.IGeradorRelatorioDiario;
import interfaces.reportGerator.IGeradorRelatorioMensal;
import modelo.Tarefa;
import relatorios.GeradorDeRelatorios;
import servico.SessionManager;
import servico.TarefaServico;

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
	private JTextField textFieldData;
    private final TarefaServico tarefaServico = new TarefaServico();

	public Exportar() throws Exception {
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
        		Home home = new Home();
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
	            String destinatario = textFieldEmail.getText().trim();
	            String data = textFieldData.getText().trim();
	            
	            
	            if (!destinatario.isEmpty()) {
	                try {
	                	
	                	LocalDate dataFormatada = LocalDate.parse(data);
	                    String mensagem = "Segue o relatório de tarefas em anexo.";
						String nomeArquivo = "relatorio-" + dataFormatada + ".pdf";
	                    List<Tarefa>todasTarefas = tarefaServico.listarTarefa();

						IGeradorRelatorioDiario gerador = GeradorDeRelatorios.createDailyGenerator("PDF");
						gerador.gerarRelatorioDiario(todasTarefas, dataFormatada, nomeArquivo);

	                    Mensageiro.enviarEmail(destinatario, mensagem, nomeArquivo);
	                    
	                    JOptionPane.showMessageDialog(btnEnviar, 
	                        "E-mail enviado com sucesso!", 
	                        "Sucesso", 
	                        JOptionPane.INFORMATION_MESSAGE);
	                } catch (Exception ex) {
	                    JOptionPane.showMessageDialog(btnEnviar, 
	                        "Erro ao enviar e-mail: " + ex.getMessage(), 
	                        "Erro", 
	                        JOptionPane.ERROR_MESSAGE);
	                    ex.printStackTrace();
	                }
	            } else {
	                JOptionPane.showMessageDialog(btnEnviar, 
	                    "Preencha o campo de Email!", 
	                    "Erro", 
	                    JOptionPane.WARNING_MESSAGE);
	            }
	        }
	    });
	    btnEnviar.setBounds(324, 279, 117, 35);
	    email.add(btnEnviar);
	    
	    textFieldData = new JTextField();
	    textFieldData.setColumns(10);
	    textFieldData.setBounds(277, 228, 224, 31);
	    email.add(textFieldData);
	    
	    JLabel lblNewLabel_1_1 = new JLabel("Digite a data das Tarefas:");
	    lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
	    lblNewLabel_1_1.setBounds(317, 191, 167, 14);
	    email.add(lblNewLabel_1_1);
	    
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
		
		JLabel lblNewLabel_2_1 = new JLabel("Exportação Com Um Mes Especifico");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2_1.setBounds(10, 207, 350, 47);
		planilha.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("Digite a Mes Especifico (YYYY-MM)");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_3_1.setBounds(260, 275, 350, 22);
		planilha.add(lblNewLabel_3_1);
		
		textFieldMesEspecifico = new JTextField();
		textFieldMesEspecifico.setColumns(10);
		textFieldMesEspecifico.setBounds(297, 306, 150, 27);
		planilha.add(textFieldMesEspecifico);
		
		JButton btnMesEspecifico = new JButton("Exportar");
		btnMesEspecifico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mesAno = textFieldMesEspecifico.getText().trim();
				if(!mesAno.isEmpty()) {
					try {
						String[] parts = mesAno.split("-");
						int ano = Integer.parseInt(parts[0]);
						int mes = Integer.parseInt(parts[1]);

						List<Tarefa> tarefas = tarefaServico.listarTarefa();

						IGeradorRelatorioMensal gerador = GeradorDeRelatorios.createMonthlyGenerator("EXCEL", tarefaServico);

						String nomeArquivo = "relatorio-" + ano + "-" + mes + ".xlsx";
						gerador.gerarRelatorioMensal(tarefas, ano, mes, nomeArquivo);

						JOptionPane.showMessageDialog(btnMesEspecifico, "Planilha exportada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(btnMesEspecifico, "Erro ao exportar planilha: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(btnMesEspecifico, "Preencha o campo de Mês e Ano!", "Erro", JOptionPane.WARNING_MESSAGE);
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
