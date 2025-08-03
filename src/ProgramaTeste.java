

import modelo.Tarefa;
import modelo.Subtarefa;
import persistencia.TarefaDAO;
import persistencia.SubtarefaDAO;

import java.time.LocalDate;
import java.util.List;

public class ProgramaTeste {
    public static void main(String[] args) {
        // DAOs
        TarefaDAO tarefaDAO = new TarefaDAO();
        SubtarefaDAO subtarefaDAO = new SubtarefaDAO();

        // 1. Criar uma nova tarefa
        Tarefa tarefa = new Tarefa("Estudar Java", "Revisar conceitos de POO", LocalDate.of(2025, 8, 10));
        tarefa.setPrioridade(4);

        // Salvar a tarefa no banco
        tarefaDAO.salvar(tarefa);
        System.out.println("Tarefa salva com sucesso!");

        // 2. Criar subtarefas ligadas a essa tarefa
        Subtarefa s1 = new Subtarefa("Assistir aula de herança", 50.0, LocalDate.of(2025, 8, 8), tarefa);
        Subtarefa s2 = new Subtarefa("Fazer exercícios", 100.0, LocalDate.of(2025, 8, 9), tarefa);
        Subtarefa s3 = new Subtarefa("Revisar código", 0.0, LocalDate.of(2025, 8, 10), tarefa);

        // Salvar as subtarefas
        subtarefaDAO.salvar(s1);
        subtarefaDAO.salvar(s2);
        subtarefaDAO.salvar(s3);
        System.out.println("Subtarefas salvas com sucesso!");

        // 3. Buscar a tarefa novamente e exibir o progresso calculado
        Tarefa tarefaSalva = tarefaDAO.buscar(tarefa.getId());

        System.out.println("\nResumo da Tarefa:");
        System.out.println("Título: " + tarefaSalva.getTitulo());
        System.out.println("Descrição: " + tarefaSalva.getDescricao());
        System.out.println("Prioridade: " + tarefaSalva.getPrioridade());
        System.out.println("Progresso calculado: " + tarefaSalva.getPercentualConcluido() + "%");

        // 4. Listar subtarefas da tarefa
        List<Subtarefa> subtarefas = subtarefaDAO.listarPorTarefa(tarefaSalva);
        System.out.println("\nSubtarefas:");
        for (Subtarefa s : subtarefas) {
            System.out.println("- " + s.getDescricao() + " | " + s.getPercentualConcluido() + "%");
        }
    }
}
