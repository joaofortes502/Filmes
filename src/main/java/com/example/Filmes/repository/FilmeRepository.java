package com.example.Filmes.repository;

import java.util.List;
import com.example.Filmes.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;



public interface FilmeRepository extends JpaRepository<Filme, Long> {
    // Consultas customizadas (ex.: buscar filmes por gênero)
    List<Filme> findByGenero(String genero);

    List<Filme> findByTituloContainingIgnoreCase(String titulo);

    List<Filme> findByAno(int ano);
}
