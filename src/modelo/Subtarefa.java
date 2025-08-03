package modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subtarefa")
public class Subtarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private double percentualConcluido; // de 0 a 100

    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "tarefa_id", nullable = false)
    private Tarefa tarefa;

    public Subtarefa() {}

    public Subtarefa(String descricao, double percentualConcluido, LocalDate deadline, Tarefa tarefa) {
        this.descricao = descricao;
        this.percentualConcluido = percentualConcluido;
        this.deadline = deadline;
        this.tarefa = tarefa;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPercentualConcluido() {
        return percentualConcluido;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPercentualConcluido(double percentualConcluido) {
        this.percentualConcluido = percentualConcluido;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    @Override
    public String toString() {
        return "Subtarefa: " + descricao + " (" + percentualConcluido + "%)";
    }
}
