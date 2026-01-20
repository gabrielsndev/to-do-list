package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import modelo.Tarefa;

public class PainelSubtarefas extends JPanel {

    private static final long serialVersionUID = 1L;

    private PainelCadastroSubtarefa painelCadastro;
    private PainelListarSubtarefas painelListagem;

    public PainelSubtarefas(List<Tarefa> tarefasIniciais) throws Exception {
        setLayout(new BorderLayout());

       
        painelListagem = new PainelListarSubtarefas();
        painelCadastro = new PainelCadastroSubtarefa();

       
        JTabbedPane abasInternas = new JTabbedPane();
        abasInternas.addTab("Listar Subtarefas", painelListagem);
        abasInternas.addTab("Cadastrar Nova", painelCadastro);

        add(abasInternas, BorderLayout.CENTER);
        
       
        atualizarPainel(tarefasIniciais);
    }

    // MEDIATOR ??? S
    public void atualizarPainel(List<Tarefa> tarefas) {

        if (painelCadastro != null) {
            painelCadastro.carregarTarefas(tarefas);
        }
        
        // Atualiza a Tabela do painel de listagem
        if (painelListagem != null) {
            painelListagem.atualizarTabela(tarefas);
        }
    }
}