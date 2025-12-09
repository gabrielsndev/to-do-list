package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modelo.Tarefa;
import servico.TarefaServico; 
import model.*; 

public class PainelListarPorDia extends JPanel {

    private JTextField textFieldData;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    private TarefaServico tarefaServico;

    public PainelListarPorDia(TarefaServico servico) {
        this.tarefaServico = servico;
        
        setLayout(null);

        JLabel lblTitulo = new JLabel("Lista de Tarefas Por Dia");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitulo.setBounds(10, 11, 300, 22);
        add(lblTitulo);

        textFieldData = new JTextField();
        textFieldData.setBounds(320, 21, 136, 25);
        textFieldData.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(textFieldData);

        JLabel lblFormato = new JLabel("DD-MM-YYYY");
        lblFormato.setBounds(320, 47, 100, 13);
        lblFormato.setFont(new Font("Tahoma", Font.BOLD, 10));
        add(lblFormato);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(470, 22, 90, 23);
        add(btnBuscar);

        String[] colunas = {"ID", "Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        
        modeloTabela = new DefaultTableModel(null, colunas) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 4 || column == 6 || column == 7;
             }
        };
        
        tabela = new JTable(modeloTabela);
        this.configuringTabela();

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(10, 80, 760, 400);
        add(scrollPane);

        btnBuscar.addActionListener(e -> buscarTarefasPorDia());
    }
    
    private void configuringTabela() {
        tabela.removeColumn(tabela.getColumn("ID"));

        String[] statusOptions = {"Pendente", "Concluída", "Atrasada"};
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(new JComboBox<>(statusOptions)));
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());

        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar", TipoDAO.TAREFA));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar", TipoDAO.TAREFA));
    }

    private void buscarTarefasPorDia() {
        String texto = textFieldData.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite a data", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LocalDate data = LocalDate.parse(texto, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            List<Tarefa> tarefas = tarefaServico.buscarTarefasPorData(data); 

            modeloTabela.setRowCount(0);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
            for (Tarefa t : tarefas) {
                modeloTabela.addRow(new Object[]{
                    t.getId(),
                    t.getTitulo(),
                    t.getDeadline().format(dtf),
                    t.getDescricao(),
                    "Pendente",
                    t.getPrioridade(),
                    "Editar",
                    "Apagar"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Data inválida ou erro na busca.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}