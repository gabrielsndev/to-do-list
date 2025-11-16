package persistencia;

import jakarta.persistence.*;
import modelo.Subtarefa;
import modelo.Tarefa;

import java.util.List;

public class SubtarefaDAO {
    private final EntityManagerFactory emf;
    
    public SubtarefaDAO() {
        this.emf = Persistence.createEntityManagerFactory("todo-pu");
    }
    
    public void limparSubtarefasOrfas() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            int deletados = em.createNativeQuery(
                "DELETE FROM subtarefa WHERE tarefa_id NOT IN (SELECT id FROM tarefa)"
            ).executeUpdate();

            em.getTransaction().commit();

            System.out.println("Subtarefas órfãs deletadas: " + deletados);
        } catch (Exception e) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
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
