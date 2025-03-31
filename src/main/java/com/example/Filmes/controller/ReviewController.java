package com.example.Filmes.controller;

import com.example.Filmes.model.Review;
import com.example.Filmes.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews") // endpoint reviews
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    //Lista todas as reviews
    @GetMapping
    public ResponseEntity<List<Review>> listarTodasReviews() {
        List<Review> reviews = reviewService.listarTodasReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    //Busca review por id
    @GetMapping("/{id}")
    public ResponseEntity<Review> buscarReviewPorId(@PathVariable Long id) {
        Review review = reviewService.buscarReviewPorId(id);
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Criar Review
    @PostMapping
    public ResponseEntity<Review> criarReview(@RequestBody Review review, @RequestParam Long usuarioId, @RequestParam Long filmeId) {
        try {
            Review novaReview = reviewService.criarReview(review, usuarioId, filmeId);
            return new ResponseEntity<>(novaReview, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Atualiza review
    @PutMapping("/{id}")
    public ResponseEntity<Review> atualizarReview(@PathVariable Long id, @RequestBody Review reviewAtualizada, @RequestParam Long usuarioId) {
        try {
            Review review = reviewService.atualizarReview(id, reviewAtualizada, usuarioId);
            if (review != null) {
                return new ResponseEntity<>(review, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    //Deleta review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReview(@PathVariable Long id, @RequestParam Long usuarioId) {
        try {
            reviewService.deletarReview(id, usuarioId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}