package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modelo.Evento;
import servico.EventoServico;
import model.*;

public class PainelListarEventosPorMes extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField textFieldPorMes;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private EventoServico eventoServico;

    public PainelListarEventosPorMes(EventoServico eventoServico) {
        this.eventoServico = eventoServico;
        setLayout(null);

        JLabel lblTitulo = new JLabel("Lista de Eventos Por Mês");
        lblTitulo.setBounds(10, 11, 246, 22);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(lblTitulo);

        textFieldPorMes = new JTextField();
        textFieldPorMes.setFont(new Font("Tahoma", Font.BOLD, 15));
        textFieldPorMes.setBounds(304, 26, 136, 25);
        add(textFieldPorMes);

        JLabel labelData = new JLabel("Mês (1-12)");
        labelData.setFont(new Font("Tahoma", Font.BOLD, 10));
        labelData.setBounds(305, 49, 70, 13);
        add(labelData);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(333, 61, 90, 23);
        add(btnBuscar);

        String[] colunas = {"ID", "Titulo", "Data", "Descrição", "Status", "Editar", "Apagar"};
        modeloTabela = new DefaultTableModel(null, colunas);
        tabela = new JTable(modeloTabela);
        
        tabela.removeColumn(tabela.getColumn("ID"));
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());
        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar", TipoDAO.EVENTO));
        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar", TipoDAO.EVENTO));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(10, 95, 759, 427);
        add(scrollPane);

        btnBuscar.addActionListener(e -> buscar());
    }

    private void buscar() {
        if (textFieldPorMes.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o mês", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int mes = Integer.parseInt(textFieldPorMes.getText().trim());
            
            List<Evento> eventos = eventoServico.listarTodosOsEventosPorMes(mes);

            modeloTabela.setRowCount(0);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            for (Evento ev : eventos) {
                modeloTabela.addRow(new Object[]{
                    ev.getId(), ev.getTitulo(), ev.getData().format(dtf), ev.getDescricao(), "Pendente", "Editar", "Apagar"
                });
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um mês válido (número).", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}