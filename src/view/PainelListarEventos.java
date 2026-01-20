package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modelo.Evento;
import servico.EventoServico;
import strategyButton.EventoStrategy;
import model.*; 

public class PainelListarEventos extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private EventoServico eventoServico;

    public PainelListarEventos(EventoServico eventoServico) {
        this.eventoServico = eventoServico;
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Titulo", "Descrição", "Data", "Status", "Editar", "Apagar"};
        
        modeloTabela = new DefaultTableModel(null, colunas) {
             public boolean isCellEditable(int row, int column) {
                 return column == 4 || column == 5 || column == 6; 
             }
        };
        
        tabela = new JTable(modeloTabela);
        configurarTabela();
        atualizarTabela(); 
        
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }
    
    private void configurarTabela() {
        tabela.removeColumn(tabela.getColumn("ID"));
        
        String[] statusOptions = {"Pendente", "Concluída"};
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(new JComboBox<>(statusOptions)));
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());
        
        EventoStrategy strategy = new EventoStrategy(this.eventoServico);

        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar", strategy));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar", strategy));
    }
    
    
    
    private void atualizarTabela() {
        List<Evento> lista = eventoServico.listarTodosOsEventos();
        modeloTabela.setRowCount(0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        for (Evento e : lista) {
            String dataStr = (e.getData() != null) ? e.getData().format(dtf) : "";
            modeloTabela.addRow(new Object[]{
                e.getId(), e.getTitulo(), e.getDescricao(), dataStr, "Pendente", "Editar", "Apagar"
            });
        }
    }
}