package strategyButton;

import javax.swing.JTable;

public interface AcaoTabelaStrategy {
    void editar(JTable table, int row);
    void remover(JTable table, int row);
}