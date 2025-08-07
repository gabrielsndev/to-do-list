package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import model.ButtonRenderer;
import model.DataPrazoRender;
import model.ButtonEditor;

public class PainelListarPorDia extends JPanel {
    private JTextField textFieldData;
    
    public PainelListarPorDia() {
        setLayout(null);
        
        // Título
        JLabel lblListaDeTarefas = new JLabel("Lista de Tarefas Por Dia");
        lblListaDeTarefas.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblListaDeTarefas.setBounds(10, 11, 246, 22);
        add(lblListaDeTarefas);
        
        textFieldData = new JTextField();
        textFieldData.setBounds(320, 21, 136, 25);
        textFieldData.setFont(new Font("Tahoma", Font.BOLD, 15));
        textFieldData.setColumns(10);
        add(textFieldData);
        
        JLabel labelData = new JLabel("DD-MM-YYYY");
        labelData.setBounds(320, 47, 70, 13);
        labelData.setFont(new Font("Tahoma", Font.BOLD, 10));
        add(labelData);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarTarefasPorDia(btnBuscar);
            }
        });
        btnBuscar.setBounds(350, 62, 90, 23);
        add(btnBuscar);
        
        // Tabela de Tarefas
        criarTabelaTarefas();
    }
    
    private void criarTabelaTarefas() {
        String[] colunas = {"Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        Object[][] dados = {
            {"Estudar Java", "23-07-2025", "Descricao", "Pendente", "0", "", ""},
            {"Entregar Projeto", "18-07-2025", "Descricao", "Pendente", "5", "", ""},
            {"Entregar Projeto", "18-09-2025", "Descricao", "Pendente", "5", "", ""}
        };
        
        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(10, 102, 759, 427);
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
    
    private void buscarTarefasPorDia(JButton btnBuscar) {
        if(textFieldData.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(btnBuscar, 
                "Digite a data", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // implementar a lógica de busca por data
        String data = textFieldData.getText().trim();
        
        JOptionPane.showMessageDialog(btnBuscar, 
            "Buscando tarefas para a data: " + data, 
            "Busca", JOptionPane.INFORMATION_MESSAGE);
    }
}