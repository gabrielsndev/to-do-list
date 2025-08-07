package model;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
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
                fireEditingStopped(); // necessário pro editor funcionar direito
                if (label.equals("Editar")) {
                    String titulo = table.getValueAt(row, 0).toString(); // pega o título da tarefa
                    JOptionPane.showConfirmDialog(button, "Editar: " + titulo);
                    // aqui depois vai abrir a tela de edição
                } else if (label.equals("Apagar")) {
                    String titulo = table.getValueAt(row, 0).toString();
                    JOptionPane.showMessageDialog(button, "Apagar: " + titulo);
                    // aqui depois você chama a função pra remover do sistema
                }
            }
        });
    }

    public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        button.setText(label);
        return button;
    }

    public Object getCellEditorValue() {
        return label;
    }

    public boolean isCellEditable(EventObject e) {
        return true;
    }

    public boolean shouldSelectCell(EventObject e) {
        return true;
    }
}
