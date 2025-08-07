package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import model.ButtonRenderer;
import model.DataPrazoRender;
import model.ButtonEditor;

public class PainelSubtarefas extends JPanel {
    
    public PainelSubtarefas() {
        setLayout(new BorderLayout());
        
        JTabbedPane abasInternas = new JTabbedPane();
        
        JPanel painelListagem = criarPainelListagem();

        JPanel painelCadastro = criarPainelCadastro();
        
        abasInternas.addTab("Listar", painelListagem);
        abasInternas.addTab("Cadastrar", painelCadastro);

        add(abasInternas, BorderLayout.CENTER);
    }
    
    private JPanel criarPainelListagem() {
        JPanel painelListagem = new JPanel(new BorderLayout());
        
        String[] colunas = {"Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        Object[][] linhas = {
            {"Estudar Java", "23-07-2025", "Descricao", "Pendente", "0", "", ""},
            {"Entregar Projeto", "18-07-2025", "Descricao", "Pendente", "5", "", ""}
        };

        JTable tabela = new JTable(linhas, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);
        painelListagem.add(scrollPane, BorderLayout.CENTER);

        String[] statusOptions = {"Pendente", "Concluída", "Atrasada"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));

        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());

        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar"));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar"));
        
        return painelListagem;
    }
    
    private JPanel criarPainelCadastro() {
        JPanel painelCadastro = new JPanel();
        painelCadastro.setLayout(null);

        JLabel labelTarefaMae = new JLabel("Selecione a Tarefa: ");
        labelTarefaMae.setBounds(50, 50, 200, 25);
        painelCadastro.add(labelTarefaMae);
        
        JComboBox<String> comboBoxTarefaMae = new JComboBox<>();
        comboBoxTarefaMae.setBounds(200, 50, 300, 25);
        painelCadastro.add(comboBoxTarefaMae);

        JLabel labelTitulo = new JLabel("Titulo da Subtarefa:");
        labelTitulo.setBounds(50, 90, 200, 25);
        painelCadastro.add(labelTitulo);
        
        JTextField textTitulo = new JTextField();
        textTitulo.setBounds(200, 90, 300, 25);
        painelCadastro.add(textTitulo);

        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(50, 130, 200, 25);
        painelCadastro.add(labelDescricao);
        
        JTextField textDescricao = new JTextField();
        textDescricao.setBounds(200, 130, 300, 25);
        painelCadastro.add(textDescricao);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!textTitulo.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(btnSalvar, 
                        "Subtarefa Salva Com Sucesso", 
                        "SubTarefa Salva", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Limpar campos após salvar
                    textTitulo.setText("");
                    textDescricao.setText("");
                } else {
                    JOptionPane.showMessageDialog(btnSalvar,"É preciso adicionar um titulo a SubTarefa", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnSalvar.setBounds(250, 180, 100, 30);
        painelCadastro.add(btnSalvar);
        
        return painelCadastro;
    }
}