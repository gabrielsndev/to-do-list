package persistencia;

import jakarta.persistence.*;
import modelo.Evento;
import interfaces.repositorioInterface.EventoRepositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EventoDAO implements EventoRepositorio {
    private EntityManagerFactory emf;

    public EventoDAO() {
        this.emf = Persistence.createEntityManagerFactory("todo-pu");
    }

    public void salvar(Evento evento) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(evento);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void atualizar(Evento evento){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(evento);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void remover(long id){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Evento evento = em.find(Evento.class, id);
            if (evento != null) {
                em.remove(evento);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Optional<Evento> buscarPorId(long id){
        EntityManager em = emf.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Evento.class, id));
        } finally {
            em.close();
        }
    }

    public List<Evento> listarTodosOsEventos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Evento e ORDER BY e.data", Evento.class).getResultList();
        } finally {
            em.close();
        }
    }
    

    public List<Evento> buscarPorData(LocalDate data) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Evento e WHERE e.data = :dia", Evento.class)
                    .setParameter("dia", data)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Evento> listarPorMes(int mes) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Evento e WHERE YEAR(e.data) = :ano AND MONTH(e.data) = :mes", Evento.class)
               
                    .setParameter("mes", mes)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
