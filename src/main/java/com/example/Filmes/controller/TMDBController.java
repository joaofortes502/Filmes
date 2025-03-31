package com.example.Filmes.controller;

import com.example.Filmes.model.TMDBMovieDTO;
import com.example.Filmes.model.TMDBPopularMoviesResponse;
import com.example.Filmes.model.TMDBSingleMovieResponse;
import com.example.Filmes.service.TMDBService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tmdb")
public class TMDBController {

    private final TMDBService tmdbService;

    public TMDBController(TMDBService tmdbService) {
        this.tmdbService = tmdbService;
    }

    /**
     * Obtém filmes populares da API TMDB
     * @param page Número da página (opcional, padrão 1)
     * @return Lista de filmes populares paginados
     */
    @GetMapping("/filmes/populares")
    public ResponseEntity<TMDBPopularMoviesResponse> getPopularMovies(
            @RequestParam(defaultValue = "1") Integer page) {
        TMDBPopularMoviesResponse response = tmdbService.getPopularMovies(page).block();
        return ResponseEntity.ok(response);
    }

    /**
     * Obtém detalhes de um filme específico da API TMDB
     * @param tmdbId ID do filme no TMDB
     * @return Detalhes completos do filme
     */
    @GetMapping("/filmes/{tmdbId}")
    public ResponseEntity<TMDBSingleMovieResponse> getMovieDetails(@PathVariable Long tmdbId) {
        TMDBSingleMovieResponse response = tmdbService.getMovieDetails(tmdbId).block();
        return ResponseEntity.ok(response);
    }

    /**
     * Pesquisa filmes na API TMDB
     * @param query Termo de pesquisa
     * @param page Número da página (opcional, padrão 1)
     * @return Resultados da pesquisa paginados
     */
    @GetMapping("/filmes/pesquisar")
    public ResponseEntity<TMDBPopularMoviesResponse> searchMovies(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") Integer page) {
        TMDBPopularMoviesResponse response = tmdbService.searchMovies(query, page).block();
        return ResponseEntity.ok(response);
    }

    /**
     * Sincroniza um filme do TMDB com o banco de dados local
     * @param tmdbId ID do filme no TMDB
     * @return Filme sincronizado
     */
    @PostMapping("/filmes/sincronizar/{tmdbId}")
    public ResponseEntity<TMDBMovieDTO> syncMovie(@PathVariable Long tmdbId) {
        TMDBSingleMovieResponse movieDetails = tmdbService.getMovieDetails(tmdbId).block();

        TMDBMovieDTO movieDTO = new TMDBMovieDTO();
        movieDTO.setId(movieDetails.getId());
        movieDTO.setTitle(movieDetails.getTitle());
        movieDTO.setOverview(movieDetails.getOverview());
        movieDTO.setPoster_path(movieDetails.getPoster_path());
        movieDTO.setVote_average(movieDetails.getVote_average());
        movieDTO.setRelease_date(movieDetails.getRelease_date());

        // Converter genres para genre_ids
        if (movieDetails.getGenres() != null) {
            movieDTO.setGenre_ids(
                    movieDetails.getGenres().stream()
                            .map(TMDBSingleMovieResponse.GenreDTO::getId)
                            .toList()
            );
        }

        return ResponseEntity.ok(movieDTO);
    }
}