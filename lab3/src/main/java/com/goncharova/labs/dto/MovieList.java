package com.goncharova.labs.dto;

import java.util.List;

public class MovieList {
    private List<Movie> movies;

    public MovieList() {
    }

    public MovieList(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}