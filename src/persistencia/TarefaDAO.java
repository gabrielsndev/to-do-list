package persistencia;

import jakarta.persistence.*;
import modelo.Tarefa;
import interfaces.repositorioInterface.TarefaRepositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TarefaDAO implements TarefaRepositorio {

    private EntityManagerFactory emf;

    public TarefaDAO() {
        this.emf = Persistence.createEntityManagerFactory("todo-pu");
    }

    public void salvar(Tarefa t) {
       EntityManager em = emf.createEntityManager();
       try {
           em.getTransaction().begin();
           em.persist(t);
           em.getTransaction().commit();
       } finally {
           em.close();
       }
    }

    public List<Tarefa> listar(long idUser) {
	   EntityManager em = emf.createEntityManager();
	   try {
           return em.createQuery("SELECT t FROM Tarefa t WHERE t.user.id = :id", Tarefa.class)
                   .setParameter("id", idUser)
                   .getResultList();
       } finally {
           em.close();
       }
   }
    public List<Tarefa> listarTarefaCritica() {
       EntityManager em = emf.createEntityManager();

       try {
           return em.createQuery("SELECT t FROM Tarefa t WHERE t.critica = true", Tarefa.class).getResultList();
       } finally {
           em.close();
       }
   }
   
   public void remover(long id) throws Exception{
       EntityManager em = emf.createEntityManager();
       Tarefa t = em.find(Tarefa.class, id);
       
       try {
           Tarefa tarefa = em.find(Tarefa.class, id);
           if (tarefa != null) {
               em.getTransaction().begin();
               em.remove(tarefa);
               em.getTransaction().commit();
           }
       } finally {
           em.close();
       }
   }
   
   public Optional <Tarefa> buscar(long id) {
       EntityManager em = emf.createEntityManager();
       try {
           return Optional.ofNullable(em.find(Tarefa.class, id));
       } finally {
           em.close();
       }
   }
   
   public List<Tarefa> buscarDeadLine(LocalDate deadline) {
	   EntityManager em = emf.createEntityManager();
	   try {
		   return em.createQuery("SELECT t FROM Tarefa t WHERE t.deadline = :deadline", Tarefa.class)
				   .setParameter("deadline", deadline)
				   .getResultList();
	   } finally {
		   em.close();
	   }
   }
  
   
   public void editarTarefa(Tarefa t) {
	    EntityManager em = emf.createEntityManager();
	    
	    try {
	        em.getTransaction().begin();

            em.merge(t);
            em.getTransaction().commit();

	    } catch (RuntimeException e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        throw e;
	    } finally {
	        em.close();
	    }
	}

  
}
