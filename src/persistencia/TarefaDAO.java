package persistencia;

import jakarta.persistence.*;
import modelo.Tarefa;

import java.time.LocalDate;
import java.util.List;

public class TarefaDAO {
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
   
   public List<Tarefa> listar(){
	   EntityManager em = emf.createEntityManager();
	   
	   try {
           return em.createQuery("SELECT t FROM Tarefa t", Tarefa.class).getResultList();
       } finally {
           em.close();
       }
   }
   
   public void remover(long id) throws Exception{
       EntityManager em = emf.createEntityManager();
       Tarefa t = em.find(Tarefa.class, id);
       if(t == null) {throw new Exception();}
       
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
   
   public Tarefa buscar(long id) {
       EntityManager em = emf.createEntityManager();
       try {
           return em.find(Tarefa.class, id);
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
   
}
