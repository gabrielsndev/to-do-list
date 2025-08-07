package model;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DataPrazoRender extends DefaultTableCellRenderer {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Ajuste se estiver com outro formato

    
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Limpa qualquer cor antiga
        c.setBackground(Color.WHITE);

        try {
            if (value != null) {
                LocalDate dataConclusao = LocalDate.parse(value.toString(), FORMATTER);
                LocalDate hoje = LocalDate.now();

                long dias = ChronoUnit.DAYS.between(hoje, dataConclusao);

                // Pegando o valor da coluna "Status" (ajuste o índice se mudar a ordem da sua tabela)
                Object status = table.getValueAt(row, 3); // Supondo que a coluna 3 é "Status"

                if (!"Concluída".equalsIgnoreCase(status.toString())) {
                    if (dataConclusao.isBefore(hoje)) {
                        c.setBackground(Color.RED);
                        table.setValueAt("Atrasada", row, 3);
                        
                    } else if (dias < 3) {
                        c.setBackground(Color.YELLOW);
                        
                    } else {
                        c.setBackground(Color.GREEN);
                        
                    }
                }
            }
        } catch (Exception e) {
            // .
        }

        return c;
    }
}
