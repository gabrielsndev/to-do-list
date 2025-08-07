package view;

import javax.swing.*;
import java.awt.*;
import model.ButtonRenderer;
import model.DataPrazoRender;
import model.ButtonEditor;

public class PainelListarTarefas extends JPanel {
    
    public PainelListarTarefas() {
        setLayout(new BorderLayout());
        
        String[] colunas = {"Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        Object[][] dados = {
            {"Estudar Java", "23-07-2025", "Descricao", "Pendente", "0", "", ""},
            {"Entregar Projeto", "18-07-2025", "Descricao", "Pendente", "5", "", ""},
            {"Entregar Projeto", "18-09-2025", "Descricao", "Pendente", "5", "", ""}
        };

        JTable tabela = new JTable(dados, colunas);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        
        String[] statusOptions = {"Pendente", "Concluída", "Atrasada"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));
        
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());
        
        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar"));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar"));
    }
}