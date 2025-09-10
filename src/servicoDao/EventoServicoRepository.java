package servicoDao;

import modelo.Evento;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

public interface EventoServicoRepository {
    void salvar(Evento evento) throws  Exception;
    void atualizar(Evento evento) throws  Exception;
    void remover(long id) throws  Exception;
    List<Evento> listarTodosOsEventos();
    Optional <Evento> buscarPorId(long id);
    List<Evento> buscarPorData(LocalDate data);
    List<Evento> listarPorMes(int mes);



}
