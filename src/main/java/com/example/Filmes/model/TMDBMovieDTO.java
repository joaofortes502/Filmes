package com.example.Filmes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TMDBMovieDTO {
    private Long id;
    private String title;
    private String overview;
    private String poster_path;
    private Double vote_average;
    private String release_date;
    private List<Integer> genre_ids;

    @JsonIgnore
    public LocalDate getReleaseDateAsLocalDate() {
        return release_date != null ? LocalDate.parse(release_date) : null;
    }
}