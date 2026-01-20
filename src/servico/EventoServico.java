package servico;

import modelo.Evento;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import interfaces.repositorioInterface.EventoRepositorio;


public class EventoServico{

    private final EventoRepositorio eventoRepositorio;

    public EventoServico(EventoRepositorio eventoRepositorio) {
        this.eventoRepositorio = eventoRepositorio;
    }

    public void criarEvento(Evento evento) throws Exception {
        List<Evento> eventosNaData = eventoRepositorio.buscarPorData(evento.getData());

        if(!eventosNaData.isEmpty()){
            throw new Exception("Já existe um evento agendado para essa data");
        }
        eventoRepositorio.salvar(evento);
        }
    
    public List<Evento> buscarEventosPorData(LocalDate data) {
     
        return eventoRepositorio.buscarPorData(data);
    }

    public void atualizarEvento(Evento evento) throws Exception {
        List<Evento> eventosNaData = eventoRepositorio.buscarPorData(evento.getData());
        for (Evento existente : eventosNaData) {
            if (!existente.getId().equals(evento.getId())) {
                throw new Exception("Já existe outro evento agendado para esta nova data.");
            }
        }
        eventoRepositorio.atualizar(evento);
    }

    public void cancelarEvento(long id) throws Exception {
        Optional<Evento> optionalEvento = eventoRepositorio.buscarPorId(id);

        if (optionalEvento.isPresent()) {
            Evento evento = optionalEvento.get();
            eventoRepositorio.remover(evento.getId());

        } else {
            throw new Exception("Evento não encontrado para exclusão.");
        }
    }

    public List<Evento> listarTodosOsEventos() {
        return eventoRepositorio.listarTodosOsEventos();
    }

    public List<Evento> listarTodosOsEventosPorMes(int mes) {
        return eventoRepositorio.listarPorMes(mes);
    }

    public long calcularDiasRestantes(Evento evento) {
        return java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.now(), evento.getData());
    }
}
