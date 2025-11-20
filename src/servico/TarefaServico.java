package servico;

import modelo.Tarefa;
import interfaces.repositorioInterface.TarefaRepositorio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TarefaServico implements interfaces.ICalculadorProgresso {

    private final TarefaRepositorio tarefaRepositorio;
    
    // 1. CONSTANTES: Facilita a manutenção. Se a regra mudar, você só altera aqui.
    private static final int PRIORIDADE_MIN = 0;
    private static final int PRIORIDADE_MAX = 5;

    public TarefaServico(TarefaRepositorio tarefaRepositorio) {
        this.tarefaRepositorio = tarefaRepositorio;
    }

    // --- CRIAÇÃO (Fluxo Principal) ---
    public void criarNovaTarefa(String titulo, String descricao, LocalDate deadline, int prioridade) throws Exception {
        // O método conta a "história" da validação passo a passo
        validarTitulo(titulo);
        validarDataFutura(deadline);
        validarPrioridade(prioridade);
        verificarDisponibilidadeDeData(deadline);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(titulo);
        tarefa.setDescricao(descricao);
        tarefa.setDeadline(deadline);
        tarefa.setPrioridade(prioridade);
        // tarefa.setStatus("Pendente"); 

        tarefaRepositorio.salvar(tarefa);
    }

    // --- ATUALIZAÇÃO ---
    public void atualizarTarefa(Tarefa tarefa) throws Exception {
        // Reutiliza as validações para garantir consistência
        validarTitulo(tarefa.getTitulo());
        // validarDataFutura(tarefa.getDeadline()); // Opcional na edição
        validarPrioridade(tarefa.getPrioridade());
        
        // Validação específica de conflito para edição (ignora o próprio ID)
        verificarDisponibilidadeParaAtualizacao(tarefa);
        
        tarefaRepositorio.editarTarefa(tarefa);
    }

    // --- MÉTODOS AUXILIARES (Regras de Negócio Isoladas) ---

    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }
    }

    private void validarDataFutura(LocalDate deadline) {
        if (deadline.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data não pode ser no passado.");
        }
    }

    private void validarPrioridade(int prioridade) {
        // Uso das constantes para evitar números mágicos no código
        if (prioridade < PRIORIDADE_MIN || prioridade > PRIORIDADE_MAX) {
            throw new IllegalArgumentException(
                String.format("A prioridade deve ser entre %d e %d.", PRIORIDADE_MIN, PRIORIDADE_MAX)
            );
        }
    }

    private void verificarDisponibilidadeDeData(LocalDate deadline) throws Exception {
        List<Tarefa> conflitos = tarefaRepositorio.buscarDeadLine(deadline);
        if (!conflitos.isEmpty()) {
            throw new Exception("Já existe uma tarefa marcada para esta data (" + deadline + ").");
        }
    }

    private void verificarDisponibilidadeParaAtualizacao(Tarefa tarefa) throws Exception {
        List<Tarefa> tarefasNaData = tarefaRepositorio.buscarDeadLine(tarefa.getDeadline());
        for (Tarefa existente : tarefasNaData) {
            // Se existe alguém nessa data, e esse alguém NÃO SOU EU (IDs diferentes)
            if (!existente.getId().equals(tarefa.getId())) {
                throw new Exception("Já existe outro evento agendado para esta data.");
            }
        }
    }

    // --- OUTROS MÉTODOS (Persistência e Listagem) ---

    public void excluir(long id) throws Exception {
        Optional<Tarefa> optionalBuscarId = tarefaRepositorio.buscar(id);
        if (optionalBuscarId.isPresent()) {
            tarefaRepositorio.remover(id);
            System.out.println("Tarefa removida com sucesso!");
        } else {
            throw new Exception("Tarefa não encontrada!");
        }
    }

    public List<Tarefa> listarTarefa() {
        return tarefaRepositorio.listar();
    }
    
    public List<Tarefa> listarTarefaCritica(List<Tarefa> tarefas) {
        LocalDate hoje = LocalDate.now();
        List<Tarefa> criticas = new ArrayList<>();
        for (Tarefa t : tarefas) {
            if (t.getDeadline() == null) continue;
            
            long dias = ChronoUnit.DAYS.between(hoje, t.getDeadline());
            
            // Assumindo que getDiasCriticos() retorna Integer (pode ser null)
            if (t.getDiasCriticos() != null && dias <= t.getDiasCriticos()) {
                criticas.add(t);
            }
        }
        return criticas;
    }

    // --- CÁLCULO DE PROGRESSO (Modelo Rico) ---
    @Override
    public double calcularPercentualConcluido(Tarefa tarefa) {
        // DELEGAÇÃO: O serviço pergunta para a Tarefa qual é o progresso dela.
        // O Serviço não precisa saber a matemática da média.
        
        // OBS: Certifique-se de ter criado o método getProgresso() na classe Tarefa
        return tarefa.getProgresso(); 
    }
    
    // Método helper para compatibilidade (opcional)
    public void criarTarefa(Tarefa tarefa) throws Exception {
        criarNovaTarefa(tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getDeadline(), tarefa.getPrioridade());
    }
}