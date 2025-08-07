package model;

import persistencia.TarefaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean clicked;
    private int row;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, JTable table, String label) {
        super(checkBox);
        this.button = new JButton(label);
        this.label = label;
        this.table = table;

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();

                if (label.equals("Editar")) {
                    String titulo = table.getValueAt(row, 1).toString(); // coluna 1: título
                    JOptionPane.showMessageDialog(button, "Editar: " + titulo);
                    // Pode abrir tela de edição aqui depois
                } else if (label.equals("Apagar")) {
                    try {
                        int confirm = JOptionPane.showConfirmDialog(button,
                            "Deseja realmente apagar esta tarefa?",
                            "Confirmação",
                            JOptionPane.YES_NO_OPTION);

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Coluna 0: ID oculto
                            Object idObj = table.getModel().getValueAt(row, 0);
                            long id = Long.parseLong(idObj.toString());

                            TarefaDAO dao = new TarefaDAO();
                            dao.remover(id);

                            ((DefaultTableModel) table.getModel()).removeRow(row);

                            JOptionPane.showMessageDialog(button, "Tarefa removida com sucesso.");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(button,
                            "Erro ao remover a tarefa.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        button.setText(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject e) {
        return true;
    }
}
