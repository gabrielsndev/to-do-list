package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import modelo.Subtarefa;
import modelo.Tarefa;
import servico.SubtarefaServico;
import model.*; // Seus renderers e editors (ButtonRenderer, DataPrazoRender, etc)

public class PainelListarSubtarefas extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private SubtarefaServico servico;

    public PainelListarSubtarefas() throws Exception {
        setLayout(new BorderLayout());
        this.servico = new SubtarefaServico(); 

        String[] colunas = {"ID", "Título", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        
        modeloTabela = new DefaultTableModel(null, colunas) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 6 || column == 7;
            }
        };

        tabela = new JTable(modeloTabela);
        configurarTabela();
        
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private void configurarTabela() {
        tabela.removeColumn(tabela.getColumn("ID"));

        String[] statusOptions = {"Pendente", "Concluída", "Atrasada"};
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(new JComboBox<>(statusOptions)));
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());

        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar", null));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
//        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar", TipoDAO.TAREFA) {
////             private static final long serialVersionUID = 1L;
////             
////             // Lógica de apagar encapsulada aqui (pode ser movida para Command depois)
////             protected void onClick(JTable table, int row) {
////                 int confirm = JOptionPane.showConfirmDialog(table, "Excluir subtarefa?", "Confirmar", JOptionPane.YES_NO_OPTION);
////                 if (confirm == JOptionPane.YES_OPTION) {
////                     try {
////                         int modelRow = table.convertRowIndexToModel(row);
////                         String id = table.getModel().getValueAt(modelRow, 0).toString();
////                         servico.remover(id); // Assume que recebe String ou Long conforme seu DAO
////                         ((DefaultTableModel) table.getModel()).removeRow(modelRow);
////                     } catch (Exception e) {
////                         e.printStackTrace();
////                     }
////                 }
////             }
////        });
}

    // Método chamado pelo Pai para atualizar a tabela
    public void atualizarTabela(List<Tarefa> tarefasPai) {
        modeloTabela.setRowCount(0);
        
        if (tarefasPai != null && !tarefasPai.isEmpty()) {
            List<Subtarefa> subs = servico.listarSubtarefas(tarefasPai);
            
            for (Subtarefa s : subs) {
                modeloTabela.addRow(new Object[] {
                    s.getId(), 
                    s.getTitulo(), 
                    null, //data
                    s.getDescricao(), 
                    "Pendente", 
                    "0", 
                    "Editar", 
                    "Apagar"
                });
            }
        }
    }
}