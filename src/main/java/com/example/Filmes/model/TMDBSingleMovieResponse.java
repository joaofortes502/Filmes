package com.example.Filmes.model;

import lombok.Data;

import java.util.List;

@Data
public class TMDBSingleMovieResponse {
    private Long id;
    private String title;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private Double vote_average;
    private Integer vote_count;
    private String release_date;
    private Integer runtime;
    private List<GenreDTO> genres;
    private String imdb_id;

    @Data
    public static class GenreDTO {
        private Integer id;
        private String name;
    }
}
