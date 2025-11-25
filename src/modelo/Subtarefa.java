package modelo;

import java.time.LocalDate;

public class Subtarefa {

    private String id;
    private String titulo;
    private String descricao;


    public Subtarefa() {}

    public Subtarefa(String titulo, String descricao, LocalDate deadline) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setId(String id){
        this.id = id;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Subtarefa: " + titulo;
    }
}
