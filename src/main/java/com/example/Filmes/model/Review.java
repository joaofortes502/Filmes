package com.example.Filmes.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nota; // 1 a 5
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "usuario_id") // Nome da coluna de chave estrangeira
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    private Filme filme;
}