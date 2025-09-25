package view;
import servico.TarefaServico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.List;
import modelo.Tarefa;
import java.time.format.DateTimeFormatter;

import java.awt.*;
import model.*;
import persistencia.TarefaDAO;

public class PainelListarCriticas extends JPanel {
	
    public PainelListarCriticas() {
    	dao = new TarefaDAO();
        setLayout(null);

        JLabel lblNewLabel = new JLabel("Listagem de Tarefas Criticas");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(10, 11, 300, 28);
        add(lblNewLabel);

        criarTabelaTarefasCriticas();
    }
    
    private List<Tarefa> tarefasCriticas;
    private TarefaDAO dao;


    private void criarTabelaTarefasCriticas() {
        String[] colunas = {"ID", "Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        

        
        tarefasCriticas = carregarDados();

        Object[][] dados = new Object[tarefasCriticas.size()][8];

        for (int i = 0; i < tarefasCriticas.size(); i++) {
            Tarefa t = tarefasCriticas.get(i);
            dados[i][0] = t.getId();
            dados[i][1] = t.getTitulo();
            
            if (t.getDeadline() != null) {
                dados[i][2] = t.getDeadline().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } else {
                dados[i][2] = "";
            }
            
            dados[i][3] = t.getDescricao();
            dados[i][4] = "Pendente"; // ajuste se tiver campo status
            dados[i][5] = t.getPrioridade();
            dados[i][6] = "Editar";
            dados[i][7] = "Apagar";
        }

        DefaultTableModel model = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // deixa só as colunas Editar e Apagar editáveis (6 e 7)
                return column == 6 || column == 7;
            }
        };

        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(10, 77, 759, 427);
        add(scrollPane);

        tabela.removeColumn(tabela.getColumn("ID")); // esconde coluna ID

        // ComboBox Status
        String[] statusOptions = {"Pendente", "Concluída", "Atrasada"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));

        // Render da data
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());

        // Botão Editar
        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar",TipoDAO.TAREFA));

        // Botão Apagar
        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar",TipoDAO.TAREFA));
    }
    
    public void atualizarTabelaCriticas() {
    	removeAll();
    	JLabel lblNewLabel = new JLabel("Listagem de Tarefas Criticas");
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    	lblNewLabel.setBounds(10, 11, 300, 28);
    	add(lblNewLabel);
    	criarTabelaTarefasCriticas();
    	revalidate();
    	repaint();
    }
    
    public List<Tarefa> carregarDados() {
    	List<Tarefa> todas = dao.listar();
    	return new TarefaServico(dao).listarTarefaCritica(todas);
    }
    
    
}
