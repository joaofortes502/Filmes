package com.example.Filmes.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long tmdbId;

    private String titulo;
    private String descricao;
    private LocalDate dataLancamento;
    private String posterPath;
    private Double mediaAvaliacao;
    private Integer duracaoMinutos;
    private String imdbId;

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "filme_generos", joinColumns = @JoinColumn(name = "filme_id"))
    @Column(name = "genero_id")
    private List<Integer> generosIds = new ArrayList<>();
}