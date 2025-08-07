package persistencia;

import jakarta.persistence.*;
import modelo.Evento;

import java.time.LocalDate;
import java.util.List;

public class EventoDAO {
    private EntityManagerFactory emf;

    public EventoDAO() {
        this.emf = Persistence.createEntityManagerFactory("todo-pu");
    }
    
    
    
    // ✅ Salvar novo evento
    public void salvar(Evento evento) throws Exception {
        if (existeEventoNaData(evento.getData())) {
            throw new Exception("Já existe um evento agendado para esta data.");
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(evento);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // ✅ Atualizar evento existente
    public void editar(Evento evento) throws Exception {
        if (existeOutroEventoNaData(evento)) {
            throw new Exception("Já existe outro evento na data informada.");
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(evento);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // ✅ Remover evento por ID
    public void remover(long id) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            Evento evento = em.find(Evento.class, id);
            if (evento == null) {
                throw new Exception("Evento não encontrado para exclusão.");
            }

            em.getTransaction().begin();
            em.remove(evento);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    

    // ✅ Buscar evento por ID
    public Evento buscar(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            em.close();
        }
    }

    // ✅ Listar todos os eventos
    public List<Evento> listar() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Evento e ORDER BY e.data", Evento.class).getResultList();
        } finally {
            em.close();
        }
    }

    // ✅ Listar eventos de um dia específico
    public List<Evento> listarPorDia(LocalDate dia) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Evento e WHERE e.data = :dia", Evento.class)
                    .setParameter("dia", dia)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ✅ Listar eventos de um mês específico
    public List<Evento> listarPorMes(int ano, int mes) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Evento e WHERE YEAR(e.data) = :ano AND MONTH(e.data) = :mes", Evento.class)
                    .setParameter("ano", ano)
                    .setParameter("mes", mes)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ✅ Verifica se já existe evento no mesmo dia (para impedir conflitos)
    public boolean existeEventoNaData(LocalDate data) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(e) FROM Evento e WHERE e.data = :data", Long.class)
                    .setParameter("data", data)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // ✅ Verifica se há outro evento na mesma data ao editar (exclui ele mesmo)
    public boolean existeOutroEventoNaData(Evento evento) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(e) FROM Evento e WHERE e.data = :data AND e.id <> :id", Long.class)
                    .setParameter("data", evento.getData())
                    .setParameter("id", evento.getId())
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
