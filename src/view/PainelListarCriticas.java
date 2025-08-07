package view;

import javax.swing.*;
import java.awt.*;
import model.ButtonRenderer;
import model.DataPrazoRender;
import model.ButtonEditor;

public class PainelListarCriticas extends JPanel {
    
    public PainelListarCriticas() {
        setLayout(null);
        
        
        JLabel lblNewLabel = new JLabel("Listagem de Tarefas Criticas");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(10, 11, 247, 28);
        add(lblNewLabel);
        
        // Criando a tabela
        criarTabelaTarefasCriticas();
    }
    
    private void criarTabelaTarefasCriticas() {
        String[] colunas = {"Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        Object[][] dados = {
            {"Estudar Java", "23-07-2025", "Descricao", "Pendente", "0", "", ""},
            {"Entregar Projeto", "18-07-2025", "Descricao", "Pendente", "5", "", ""},
            {"Entregar Projeto", "18-09-2025", "Descricao", "Pendente", "5", "", ""}
        };

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(10, 77, 759, 427);
        add(scrollPane);
        
        // ComboBox para Status
        String[] statusOptions = {"Pendente", "Concluída", "Atrasada"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));
        
        // Renderer para Data
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());
        
        // Botões de Ação
        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar"));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar"));
    }
}