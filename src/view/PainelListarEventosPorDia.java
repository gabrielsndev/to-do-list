package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modelo.Evento;
import servico.EventoServico;
import strategyButton.EventoStrategy;
import model.*;

public class PainelListarEventosPorDia extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField textFieldPorDia;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private EventoServico eventoServico;

    public PainelListarEventosPorDia(EventoServico eventoServico) {
        this.eventoServico = eventoServico;
        setLayout(null);

        JLabel lblTitulo = new JLabel("Lista de Eventos Por Dia");
        lblTitulo.setBounds(10, 11, 250, 22);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(lblTitulo);

        textFieldPorDia = new JTextField();
        textFieldPorDia.setFont(new Font("Tahoma", Font.BOLD, 15));
        textFieldPorDia.setBounds(320, 15, 136, 25);
        add(textFieldPorDia);

        JLabel labelData = new JLabel("DD-MM-YYYY");
        labelData.setFont(new Font("Tahoma", Font.BOLD, 10));
        labelData.setBounds(320, 38, 90, 13);
        add(labelData);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(350, 55, 90, 23);
        add(btnBuscar);

        String[] colunas = {"ID", "Titulo", "Data", "Descrição", "Status", "Editar", "Apagar"};
        modeloTabela = new DefaultTableModel(null, colunas);
        tabela = new JTable(modeloTabela);
        
        configurarTabela();

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(10, 95, 759, 427);
        add(scrollPane);

        btnBuscar.addActionListener(e -> buscar());
    }

    private void configurarTabela() {
        tabela.removeColumn(tabela.getColumn("ID"));
        
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"Pendente", "Concluída"})));
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());
        
        EventoStrategy strategy = new EventoStrategy(this.eventoServico);

        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar", strategy));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar", strategy));
    }

    private void buscar() {
        String texto = textFieldPorDia.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite a data.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dataBusca = LocalDate.parse(texto, dtf);
            
            List<Evento> eventos = eventoServico.buscarEventosPorData(dataBusca);

            modeloTabela.setRowCount(0);

            for (Evento evento : eventos) {
                modeloTabela.addRow(new Object[]{
                    evento.getId(),
                    evento.getTitulo(),
                    evento.getData().format(dtf),
                    evento.getDescricao(),
                    "Pendente",
                    "Editar",
                    "Apagar"
                });
            }
            
            if (eventos.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Nenhum evento encontrado nesta data.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Data inválida (DD-MM-YYYY) ou erro.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}