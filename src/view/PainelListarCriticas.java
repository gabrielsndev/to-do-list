package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modelo.Tarefa;
import model.*; // ButtonEditor, ButtonRenderer, etc.

public class PainelListarCriticas extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    private final String[] colunas = {"ID", "Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};

    public PainelListarCriticas(List<Tarefa> tarefasIniciais) {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Listagem de Tarefas Críticas");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(10, 11, 300, 28);
        add(lblTitulo);

        // 1. Configura a estrutura da tabela (Uma única vez)
        configurarTabela();
        
        //  Preenchendo com os dados iniciais
        atualizarTabelaCriticas(tarefasIniciais);
    }

    private void configurarTabela() {
        modeloTabela = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Apenas colunas Editar(6) e Apagar(7) e Status(4) são editáveis
                return column == 4 || column == 6 || column == 7;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(10, 77, 759, 427);
        add(scrollPane);

        tabela.removeColumn(tabela.getColumn("ID")); 

        String[] statusOptions = {"Pendente", "Concluída", "Atrasada"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));

        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());

        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar", TipoDAO.TAREFA));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar", TipoDAO.TAREFA));
    }
    
    // Apenas troca os dados, não redesenha a tela toda
    public void atualizarTabelaCriticas(List<Tarefa> tarefas) {
        // Limpa as linhas atuais
        modeloTabela.setRowCount(0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Adiciona as novas linhas
        for (Tarefa t : tarefas) {
            String dataFormatada = "";
            if (t.getDeadline() != null) {
                dataFormatada = t.getDeadline().format(formatter);
            }

            Object[] linha = {
                t.getId(),
                t.getTitulo(),
                dataFormatada,
                t.getDescricao(),
                "Pendente", // Idealmente viria de t.getStatus()
                t.getPrioridade(),
                "Editar",
                "Apagar"
            };
            
            modeloTabela.addRow(linha);
        }
    }
}