package com.example.Filmes.repository;

import com.example.Filmes.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Busca todas as reviews de um filme específico
    List<Review> findByFilmeId(Long filmeId);

    // Busca reviews de um usuário específico
    List<Review> findByUsuarioId(Long usuarioId);

    // Calcula a média de notas de um filme (consulta customizada)
    @Query("SELECT AVG(r.nota) FROM Review r WHERE r.filme.id = :filmeId")
    Double calcularMediaNotasPorFilme(@Param("filmeId") Long filmeId);

    // Verifica se um usuário já avaliou um filme
    boolean existsByUsuarioIdAndFilmeId(Long usuarioId, Long filmeId);
}