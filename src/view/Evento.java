package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import model.ButtonEditor;
import model.ButtonRenderer;
import model.DataPrazoRender;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class Evento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldTitulo;
	private JTextField textFieldDescricao;
	private JTextField textFieldDataInicial;
	private JTextField textFieldDataFinal;
	private JTable table;
	private JTextField textFieldPorMes;
	private JTextField textFieldPorDia;


	public Evento() {
		setTitle("Evento");
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
		tabbedPane.addTab("Cadastro de Evento ",EventoCadastro());
		tabbedPane.addTab("Listar Evento", criarPainelListar());
		tabbedPane.addTab("Listar Evento Por Mes", painelListarEventosPorMes());
		tabbedPane.addTab("Listar Evento Por Dia", painelListarEventosPorDia());
		getContentPane().add(tabbedPane);
        setVisible(true);
        
		// contentPane.add(tabbedPane);
	}
	
	private JPanel EventoCadastro() {
		Date dataHoje = new Date();
		SimpleDateFormat formatacaoData = new SimpleDateFormat("dd-MM-yyyy");
		String dataFormatada = formatacaoData.format(dataHoje);
		
		JPanel painelCadastro = new JPanel();
		painelCadastro.setLayout(null);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setText("");
		textFieldTitulo.setBounds(208, 80, 176, 20);
		painelCadastro.add(textFieldTitulo);
		textFieldTitulo.setColumns(10);
		
		JLabel labelTitulo = new JLabel("Digite o Titulo do Evento:");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelTitulo.setBounds(22, 73, 176, 31);
		painelCadastro.add(labelTitulo);
		
		JLabel labelDescricao = new JLabel("Digite a Descrição do Evento:");
		labelDescricao.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelDescricao.setBounds(22, 134, 200, 31);
		painelCadastro.add(labelDescricao);
		
		textFieldDescricao = new JTextField();
		textFieldDescricao.setText("");
		textFieldDescricao.setColumns(10);
		textFieldDescricao.setBounds(232, 141, 233, 20);
		painelCadastro.add(textFieldDescricao);
		
		JLabel labelDataInicial = new JLabel("Data Do Cadastro do Evento:");
		labelDataInicial.setFont(new Font("Tahoma", Font.BOLD, 14));
		//colocar a data de hj usando a variavel dataHoje na label dataInicial.
		labelDataInicial.setBounds(22, 201, 219, 31);
		painelCadastro.add(labelDataInicial);
		
		textFieldDataInicial = new JTextField();
		textFieldDataInicial.setText(dataFormatada);
		textFieldDataInicial.setColumns(10);
		textFieldDataInicial.setBounds(232, 208, 71, 20);
		painelCadastro.add(textFieldDataInicial);
		
		JLabel labelDataConclusao = new JLabel("Digite a data que acontecerá o Evento:");
		labelDataConclusao.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelDataConclusao.setBounds(22, 256, 281, 31);
		painelCadastro.add(labelDataConclusao);
		
		
		MaskFormatter mascaraData = null;
		try {
			mascaraData = new MaskFormatter("##-##-####");
			mascaraData.setPlaceholder("_");
		}catch(java.text.ParseException e) {
			e.printStackTrace();
		}
		
		textFieldDataFinal = new JFormattedTextField(mascaraData);
		textFieldDataFinal.setBounds(300, 263, 80, 20);
		textFieldDataFinal.setColumns(10);
		painelCadastro.add(textFieldDataFinal);

		
		JButton btnSalvar = new JButton("Cadastrar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textFieldTitulo.getText().isEmpty() && !textFieldDescricao.getText().isEmpty() &&
						!textFieldDataFinal.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnSalvar, "Evento Cadastrado com Sucesso!", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(btnSalvar, "Preencha os campos obrigatorios!\n Titulo, Descricao e Data Final", "Erro",JOptionPane.WARNING_MESSAGE);
				}// Adicionar a verificação das datas pra ver se já tem um evento com a mesma data.
				return;
			}
		});
		
		btnSalvar.setBounds(263, 331, 105, 31);
		painelCadastro.add(btnSalvar);
		
		return painelCadastro;
	}
    private JPanel criarPainelListar() {
        JPanel painelTarefas = new JPanel(new BorderLayout());

        String[] colunas = {"Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        Object[][] dados = {
        		{"Estudar Java", "23-07-2025", "Descricao", "Pendente", "0", "", ""},
                {"Entregar Projeto", "18-07-2025", "Descricao", "Pendente", "5", "", ""},
                {"Entregar Projeto", "18-08-2025", "Descricao", "Pendente", "5", "", ""},
                {"Entregar Projeto", "18-10-2025", "Descricao", "Pendente", "5", "", ""}
        };

        JTable tabela = new JTable(dados, colunas);
        painelTarefas.add(new JScrollPane(tabela), BorderLayout.CENTER);
        
        String[] statusOptions = { "Pendente", "Concluída" };
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));
        
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());
        
        // Botões de Ação nas colunas "Editar" e "Apagar"
        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar"));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar"));

        
        return painelTarefas;
    }
    
    private JPanel painelListarEventosPorMes() {
    	JPanel listarEventoPMes = new JPanel();
    	listarEventoPMes.setLayout(null);
    	
    	
    	JLabel lblNewLabel = new JLabel("Lista de Eventos Por Mes");
    	lblNewLabel.setBounds(10, 11, 246, 22);
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
    	listarEventoPMes.add(lblNewLabel);
    	
    	String[] colunas = {"Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        Object[][] dados = {
        		{"Estudar Java", "23-07-2025", "Descricao", "Pendente", "0", "", ""},
                {"Entregar Projeto", "18-07-2025", "Descricao", "Pendente", "5", "", ""},
                {"Entregar Projeto", "18-08-2025", "Descricao", "Pendente", "5", "", ""},
                {"Entregar Projeto", "18-10-2025", "Descricao", "Pendente", "5", "", ""}
        };

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(10, 95, 759, 427);
        listarEventoPMes.add(scrollPane);
        
        String[] statusOptions = { "Pendente", "Concluída","Atrasada" };
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));
        
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());
        
        // Botões de Ação nas colunas "Editar" e "Apagar"
        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar"));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar"));
        
        textFieldPorMes = new JTextField();
        textFieldPorMes.setFont(new Font("Tahoma", Font.BOLD, 15));
        textFieldPorMes.setColumns(10);
        textFieldPorMes.setBounds(304, 26, 136, 25);
        listarEventoPMes.add(textFieldPorMes);
        
        JLabel labelData = new JLabel("DD-MM-YYYY");
        labelData.setFont(new Font("Tahoma", Font.BOLD, 10));
        labelData.setBounds(305, 49, 70, 13);
        listarEventoPMes.add(labelData);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(textFieldPorMes.getText().isEmpty()) {
        			JOptionPane.showMessageDialog(btnBuscar, "Digite a data","Erro",JOptionPane.WARNING_MESSAGE);
        		}
        	}
        });
        btnBuscar.setBounds(333, 61, 90, 23);
        listarEventoPMes.add(btnBuscar);
        
    	return listarEventoPMes;
    }
    
        
    private JPanel painelListarEventosPorDia() {
    	JPanel listarEventoPDia = new JPanel();
    	listarEventoPDia.setLayout(null);
    	
    	
    	JLabel lblNewLabel = new JLabel("Lista de Eventos Por Dia");
    	lblNewLabel.setBounds(10, 11, 217, 22);
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
    	listarEventoPDia.add(lblNewLabel);
    	
    	String[] colunas = {"Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        Object[][] dados = {
        		{"Estudar Java", "23-07-2025", "Descricao", "Pendente", "0", "", ""},
                {"Entregar Projeto", "18-07-2025", "Descricao", "Pendente", "5", "", ""},
                {"Entregar Projeto", "18-08-2025", "Descricao", "Pendente", "5", "", ""},
                {"Entregar Projeto", "18-10-2025", "Descricao", "Pendente", "5", "", ""}
        };

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(10, 95, 759, 427);
        listarEventoPDia.add(scrollPane);
        
        String[] statusOptions = { "Pendente", "Concluída","Atrasada" };
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));
        
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());
        
        // Botões de Ação nas colunas "Editar" e "Apagar"
        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar"));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar"));
        
        JLabel labelData = new JLabel("DD-MM-YYYY");
        labelData.setFont(new Font("Tahoma", Font.BOLD, 10));
        labelData.setBounds(320, 38, 70, 13);
        listarEventoPDia.add(labelData);
        
        textFieldPorDia = new JTextField();
        textFieldPorDia.setFont(new Font("Tahoma", Font.BOLD, 15));
        textFieldPorDia.setColumns(10);
        textFieldPorDia.setBounds(320, 15, 136, 25);
        listarEventoPDia.add(textFieldPorDia);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(textFieldPorDia.getText().isEmpty()) {
        			JOptionPane.showMessageDialog(btnBuscar, "Digite a data","Erro",JOptionPane.WARNING_MESSAGE);
        		}
        	}
        });
        btnBuscar.setBounds(350, 55, 90, 23);
        listarEventoPDia.add(btnBuscar);
        
    	return listarEventoPDia;
    }
    
   
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Evento frame = new Evento();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
