package com.goncharova.labs.repository;

import com.goncharova.labs.dto.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    Optional<Movie> getById(int id);
    List<Movie> getAll();
    void save(Movie movie);
}