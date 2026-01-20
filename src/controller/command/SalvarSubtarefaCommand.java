package controller.command;

import java.awt.Component;
import javax.swing.JOptionPane;
import modelo.Subtarefa;
import modelo.Tarefa;
import servico.SubtarefaServico;

public class SalvarSubtarefaCommand implements Command {

    private Component parentView;
    private String titulo;
    private String descricao;
    private Tarefa tarefaMae;
    private SubtarefaServico service;
    private boolean sucesso = false;

    public SalvarSubtarefaCommand(Component parentView, String titulo, String descricao, Tarefa tarefaMae) throws Exception {
        this.parentView = parentView;
        this.titulo = titulo;
        this.descricao = descricao;
        this.tarefaMae = tarefaMae;

        this.service = new SubtarefaServico(); 
    }

    @Override
    public void execute() {
        this.sucesso = false;

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(parentView, "É preciso adicionar um título", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (tarefaMae == null) {
            JOptionPane.showMessageDialog(parentView, "Selecione a tarefa mãe", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Subtarefa sub = new Subtarefa();
            sub.setTitulo(titulo);
            sub.setDescricao(descricao);
            

            service.salvarNovaSubtarefa(sub, tarefaMae.getId());

            JOptionPane.showMessageDialog(parentView, "Subtarefa salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.sucesso = true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentView, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSucesso() {
        return sucesso;
    }
}