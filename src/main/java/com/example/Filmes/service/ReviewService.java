package com.example.Filmes.service;

import com.example.Filmes.model.Review;
import com.example.Filmes.model.Usuario;
import com.example.Filmes.repository.ReviewRepository;
import com.example.Filmes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Cria uma nova review
    @Transactional
    public Review criarReview(Review review, Long usuarioId, Long filmeId) {
        if (reviewRepository.existsByUsuarioIdAndFilmeId(usuarioId, filmeId)) {
            throw new RuntimeException("Usuário já avaliou este filme!");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        review.setUsuario(usuario);
        return reviewRepository.save(review);
    }

    // Lista reviews por filme
    public List<Review> listarReviewsPorFilme(Long filmeId) {
        return reviewRepository.findByFilmeId(filmeId);
    }

    // Lista reviews por usuário
    public List<Review> listarReviewsPorUsuario(Long usuarioId) {
        return reviewRepository.findByUsuarioId(usuarioId);
    }

    // Deleta uma review
    @Transactional
    public void deletarReview(Long reviewId, Long usuarioId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review não encontrada"));

        if (!review.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Ação não permitida");
        }

        reviewRepository.delete(review);
    }

    // Busca review por id
    public Review buscarReviewPorId(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review não encontrada"));
    }

    @Transactional
    public Review atualizarReview(Long reviewId, Review reviewAtualizada, Long usuarioId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review não encontrada"));

        if (!review.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Ação não permitida");
        }

        reviewAtualizada.setId(reviewId);
        reviewAtualizada.setUsuario(review.getUsuario()); // Mantém o usuário original
        reviewAtualizada.setFilme(review.getFilme()); // Mantém o filme original

        return reviewRepository.save(reviewAtualizada);
    }

    //Lista reviews
    public List<Review> listarTodasReviews() {
        return reviewRepository.findAll();
    }
}

