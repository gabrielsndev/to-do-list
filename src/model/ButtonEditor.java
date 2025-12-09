package model;

import javax.swing.*;
import java.awt.*;
import java.util.EventObject;
import strategyButton.AcaoTabelaStrategy; 

public class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private final String label;
    private int row;
    private final JTable table;
    
    
    private final AcaoTabelaStrategy strategy;

    
    public ButtonEditor(JCheckBox checkBox, JTable table, String label, AcaoTabelaStrategy strategy) {
        super(checkBox);
        this.button = new JButton(label);
        this.label = label;
        this.table = table;
        this.strategy = strategy; 

        button.addActionListener(e -> {
            fireEditingStopped();
            
            if (label.equals("Editar")) {
                strategy.editar(table, row);
            } else if (label.equals("Apagar")) {
                int confirm = JOptionPane.showConfirmDialog(button,
                        "Deseja realmente apagar este item?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    strategy.remover(table, row);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
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