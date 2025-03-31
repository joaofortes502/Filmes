package com.example.Filmes.model;

import lombok.Data;

import java.util.List;

@Data
public class TMDBPopularMoviesResponse {
    private Integer page;
    private List<TMDBMovieDTO> results;
    private Integer total_pages;
    private Integer total_results;
}