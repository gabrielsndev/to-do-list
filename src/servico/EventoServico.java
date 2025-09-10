package servico;

import modelo.Evento;
import java.util.List;
import servicoDao.EventoServicoRepository;

import java.time.LocalDate;
import java.util.Optional;


public class EventoServico{

    private final EventoServicoRepository eventoServicoRepository;

    public EventoServico(EventoServicoRepository eventoServicoRepository) {
        this.eventoServicoRepository = eventoServicoRepository;
    }

    public void criarEvento(Evento evento) throws Exception {
        List<Evento> eventosNaData = eventoServicoRepository.buscarPorData(evento.getData());

        if(eventosNaData.isEmpty()){
            throw new Exception("Já existe um evento agendado para essa data");
        }
        eventoServicoRepository.salvar(evento);
        }

    public void atualizarEvento(Evento evento) throws Exception {
        List<Evento> eventosNaData = eventoServicoRepository.buscarPorData(evento.getData());
        for (Evento existente : eventosNaData) {
            if (!existente.getId().equals(evento.getId())) {
                throw new Exception("Já existe outro evento agendado para esta nova data.");
            }
        }
        eventoServicoRepository.atualizar(evento);
    }

    public void cancelarEvento(long id) throws Exception {
        Evento evento = eventoServicoRepository.buscarPorId(id)
                .orElseThrow(() -> new Exception("Evento não encontrado para exclusão."));

        eventoServicoRepository.remover(id);
        System.out.println("Evento removido com sucesso!");
    }

    public List<Evento> listarTodosOsEventos() {
        return eventoServicoRepository.listarTodosOsEventos();
    }

    public List<Evento> listarTodosOsEventosPorMes(int mes) {
        return eventoServicoRepository.listarPorMes(mes);
    }
}
