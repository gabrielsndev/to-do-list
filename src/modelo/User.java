package modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity @Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(nullable = false, length = 60)
    private String username;

    @Column(nullable = false, length = 15)
    private String password;

    @Column(nullable = false, length = 150)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "Tarefas")
    private List<Tarefa> tarefasUsuario;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "Eventos")
    private List<Evento> eventosUsuario;
}
