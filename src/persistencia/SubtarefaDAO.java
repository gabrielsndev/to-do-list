package persistencia;

import jakarta.persistence.*;
import modelo.Subtarefa;
import modelo.Tarefa;

import java.util.List;

public class SubtarefaDAO {
    private EntityManagerFactory emf;

    public SubtarefaDAO() {
        this.emf = Persistence.createEntityManagerFactory("todo-pu");
    }

    // Salvar nova subtarefa 
    public void salvar(Subtarefa subtarefa) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(subtarefa);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Atualizar dados de uma subtarefa existente
    public void editar(Subtarefa subtarefa) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(subtarefa); // Atualiza os dados com base no ID
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Remover subtarefa pelo ID
    public void remover(long id) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            Subtarefa subtarefa = em.find(Subtarefa.class, id);
            if (subtarefa == null) {
                throw new Exception("Subtarefa não encontrada");
            }

            em.getTransaction().begin();
            em.remove(subtarefa);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Buscar uma subtarefa pelo ID
    public Subtarefa buscar(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Subtarefa.class, id);
        } finally {
            em.close();
        }
    }

    // Listar todas as subtarefas (caso queira ver todas do sistema)
    public List<Subtarefa> listar() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Subtarefa s", Subtarefa.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Listar subtarefas de uma tarefa específica
    public List<Subtarefa> listarPorTarefa(Tarefa tarefa) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT s FROM Subtarefa s WHERE s.tarefa = :tarefa", Subtarefa.class)
                    .setParameter("tarefa", tarefa)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
