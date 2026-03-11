package com.goncharova.labs.dto;

import java.util.Collection;

public class MovieList {
    private final Collection<Movie> movies;

    public MovieList(Collection<Movie> movies) {
        this.movies = movies;
    }

    public Collection<Movie> getMovies() {
        return movies;
    }

}
