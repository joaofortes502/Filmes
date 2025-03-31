package com.example.Filmes.service;

import com.example.Filmes.model.TMDBPopularMoviesResponse;
import com.example.Filmes.model.TMDBSingleMovieResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TMDBService {
    private final WebClient webClient;
    private final String apiKey;
    private final String language;
    private final String imageBaseUrl;

    public TMDBService(
            @Value("${tmdb.api.url}") String baseUrl,
            @Value("${tmdb.api.key}") String apiKey,
            @Value("${tmdb.api.language}") String language,
            @Value("${tmdb.api.image.base.url}") String imageBaseUrl) {

        this.apiKey = apiKey;
        this.language = language;
        this.imageBaseUrl = imageBaseUrl;

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public Mono<TMDBPopularMoviesResponse> getPopularMovies(Integer page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/popular")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", language)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(TMDBPopularMoviesResponse.class);
    }

    public Mono<TMDBSingleMovieResponse> getMovieDetails(Long tmdbId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", language)
                        .build(tmdbId))
                .retrieve()
                .bodyToMono(TMDBSingleMovieResponse.class);
    }

    public Mono<TMDBPopularMoviesResponse> searchMovies(String query, Integer page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", language)
                        .queryParam("query", query)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(TMDBPopularMoviesResponse.class);
    }
}