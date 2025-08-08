package modelo;

import persistencia.EventoDAO;

import java.util.List;
import email.Mensageiro;

public class EventoServico {

    private EventoDAO eventoDAO = new EventoDAO();
 
    // Criar novo evento (com validação de data única)
    public void criarEvento(Evento evento) throws Exception {
        List<Evento> eventosExistentes = eventoDAO.listar();

        for (Evento e : eventosExistentes) {
            if (e.getData().equals(evento.getData())) {
                throw new Exception("Já existe um evento marcado para essa data.");
            }
        }

        eventoDAO.salvar(evento);
    }


    public void editarEvento(Evento evento) throws Exception {
        eventoDAO.editar(evento);
        Mensageiro.enviarEmail("projetospoo@gmail.com", 
            "Evento alterado: " + evento.getTitulo() + "\n\n" + evento.toString(), null);
    }

    public void excluirEvento(long id) throws Exception {
        Evento evento = eventoDAO.buscar(id);
        if (evento == null) {
            throw new Exception("Evento não encontrado para exclusão.");
        }

        eventoDAO.remover(id);
        Mensageiro.enviarEmail("projetospoo@gmail.com", 
            "Evento cancelado: " + evento.getTitulo() + "\n\n" + evento.toString(), null);
    }

    // Listar todos os eventos
    public List<Evento> listarTodos() {
        return eventoDAO.listar();
    }

    // Listar eventos de um dia específico
    public List<Evento> listarPorDia(java.time.LocalDate dia) {
        return eventoDAO.listarPorDia(dia);
    }

    // Listar eventos de um mês específico
    public List<Evento> listarPorMes(int mes) {
        return eventoDAO.listarPorMes(mes);
    }

    // Buscar evento específico
    public Evento buscar(long id) {
        return eventoDAO.buscar(id);
    }
}
