package interfaces;

import modelo.Tarefa;

/**
 * Define um contrato para classes que sabem como calcular o progresso de uma tarefa.
 */
public interface ICalculadorProgresso {
    double calcularPercentualConcluido(Tarefa tarefa);
}
