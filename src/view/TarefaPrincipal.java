package view;

import javax.swing.*;

import controller.command.Command;
import controller.command.NavegarCommand;
import view.creators.HomeCreator;
import view.factory.IViewCreator;


import modelo.Tarefa;
import persistencia.TarefaDAO;
import servico.TarefaServico;

import interfaces.AtualizarPaineis;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TarefaPrincipal extends JFrame implements AtualizarPaineis{
	
	private JTabbedPane tabbedPane;
	
	private PainelListarTarefas painelListar;
	
	private PainelSubtarefas painelSubtarefa;
	private PainelListarCriticas painelCriticas;
	private PainelCadastrarTarefa painelCadastrar;
	
	private final TarefaDAO dao;
	private final TarefaServico servico;
    
    public TarefaPrincipal(TarefaDAO dao, TarefaServico servico) {
    	this.dao = dao;
    	this.servico = servico;
    	
        setTitle("Sistema de Tarefas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(690, 5, 80, 20);
        getContentPane().add(btnVoltar);
        
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
               IViewCreator home = new HomeCreator();
               Command navegar = new NavegarCommand(TarefaPrincipal.this, home);
               navegar.execute();

            }
        });
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 784, 561);

        
        painelListar = new PainelListarTarefas(dao.listar());
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

	    
    
    public void salvarTarefa(Tarefa tarefa) {
    	dao.salvar(tarefa);
    }

	
	public void atualizar() {
		this.painelListar.atualizarLista(dao.listar());
        
    	this.painelSubtarefa.atualizarPainel(dao.listar());
        
    	this.painelCriticas.atualizarTabelaCriticas(servico.listarTarefaCritica(dao.listar()));
			
	}

    
}