package com.example.Filmes.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Integer ano;
    private String genero;

    @Column(name = "poster_url")
    private String posterUrl;
}