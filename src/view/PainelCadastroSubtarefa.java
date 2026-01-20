package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import modelo.Tarefa;
import controller.command.Command;
import controller.command.SalvarSubtarefaCommand;

public class PainelCadastroSubtarefa extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<Tarefa> comboBoxTarefaMae;
    private JTextField textTitulo;
    private JTextField textDescricao;

    public PainelCadastroSubtarefa() {
        setLayout(null);

        JLabel labelTarefaMae = new JLabel("Selecione a Tarefa: ");
        labelTarefaMae.setBounds(50, 50, 150, 25);
        add(labelTarefaMae);

        comboBoxTarefaMae = new JComboBox<>();
        comboBoxTarefaMae.setBounds(200, 50, 300, 25);
        add(comboBoxTarefaMae);
        configurarRenderizadorCombo();

        JLabel labelTitulo = new JLabel("Título da Subtarefa:");
        labelTitulo.setBounds(50, 90, 150, 25);
        add(labelTitulo);

        textTitulo = new JTextField();
        textTitulo.setBounds(200, 90, 300, 25);
        add(textTitulo);

        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(50, 130, 150, 25);
        add(labelDescricao);

        textDescricao = new JTextField();
        textDescricao.setBounds(200, 130, 300, 25);
        add(textDescricao);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(250, 180, 100, 30);
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            Tarefa tarefaSelecionada =  (Tarefa) comboBoxTarefaMae.getSelectedItem();
             
			try {
				SalvarSubtarefaCommand cmd = new SalvarSubtarefaCommand(
				    this, 
				    textTitulo.getText().trim(), 
				    textDescricao.getText().trim(), 
				    tarefaSelecionada
				);
				cmd.execute();
				
				if (cmd.isSucesso()) {
					limparCampos();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            
        });
    }

    public void carregarTarefas(List<Tarefa> tarefas) {
        comboBoxTarefaMae.removeAllItems();
        if (tarefas != null) {
            for (Tarefa t : tarefas) {
                comboBoxTarefaMae.addItem(t);
            }
        }
    }
    
    private void limparCampos() {
        textTitulo.setText("");
        textDescricao.setText("");
    }

    private void configurarRenderizadorCombo() {
        comboBoxTarefaMae.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Tarefa) {
                    setText(((Tarefa) value).getTitulo());
                }
                return this;
            }
        });
    }
}