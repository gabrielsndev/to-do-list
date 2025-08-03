package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SubtarefaServico {

    // Filtra subtarefas incompletas (< 100%)
    public List<Subtarefa> listarIncompletas(List<Subtarefa> subtarefas) {
        return subtarefas.stream()
                .filter(s -> s.getPercentualConcluido() < 100.0)
                .collect(Collectors.toList());
    }

    // Verifica se uma subtarefa está atrasada
    public boolean estaAtrasada(Subtarefa subtarefa) {
        return subtarefa.getDeadline().isBefore(LocalDate.now()) && subtarefa.getPercentualConcluido() < 100;
    }

    // Valida valores de uma subtarefa antes de salvar
    public boolean validar(Subtarefa s) {
        return s.getPercentualConcluido() >= 0 && s.getPercentualConcluido() <= 100;
    }
}

//Você só precisa criar essa classe se houver lógica ou validações que se repetem.
// Ela é útil para organizar seu código, Evitar duplicação de lógica, Tornar testes e manutenção mais fáceis