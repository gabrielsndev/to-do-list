package view;

import javax.swing.*;

import persistencia.TarefaDAO;
import servico.TarefaServico;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TarefaPrincipal extends JFrame {
	
	private JTabbedPane tabbedPane;
	
	private PainelListarTarefas painelListar;
	
	private PainelSubtarefas painelSubtarefa;
	private PainelListarCriticas painelCriticas;
	private PainelCadastrarTarefa painelCadastrar;
	
	TarefaDAO dao = new TarefaDAO();
	TarefaServico servico = new TarefaServico(dao);
    
    public TarefaPrincipal() {
        setTitle("Sistema de Tarefas com Abas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(690, 5, 80, 20);
        getContentPane().add(btnVoltar);
        
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Home home = new Home();
                home.setVisible(true);
                home.setLocationRelativeTo(null);
            }
        });
        
        // Criando o painel de abas
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 784, 561);

        // Adicionando as abas com as telas separadas
        
        
        // colocando a lógica aqui
        
        painelListar = new PainelListarTarefas(dao.listar());
        
        // separação
        
        painelCadastrar = new PainelCadastrarTarefa(this);
        painelSubtarefa = new PainelSubtarefas(dao.listar());
        painelCriticas = new PainelListarCriticas(servico.listarTarefaCritica(dao.listar()));

        tabbedPane.addTab("Listar Tarefas", painelListar);
        tabbedPane.addTab("Cadastrar Tarefas", painelCadastrar);
        tabbedPane.addTab("Subtarefas", painelSubtarefa);
        tabbedPane.addTab("Listar Tarefa Por Dia", new PainelListarPorDia());
        tabbedPane.addTab("Listar Tarefas Criticas", painelCriticas);

        getContentPane().add(tabbedPane);
        setVisible(true);
    }

    public void atualizarTelasListagem() {
    	this.painelListar.atualizarLista(dao.listar());
    	painelSubtarefa.atualizarPainel(dao.listar());
    	painelCriticas.atualizarTabelaCriticas(servico.listarTarefaCritica(dao.listar()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TarefaPrincipal::new);
    }
}