package com.goncharova.labs.service;

import com.goncharova.labs.dto.CreateMovieRequest;
import com.goncharova.labs.dto.UniversalResponse;
import com.goncharova.labs.dto.Movie;
import com.goncharova.labs.repository.MovieRepository;
import java.util.List;

public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public UniversalResponse<List<Movie>> getAll() {
        return new UniversalResponse<>(repository.getAll());
    }

    public UniversalResponse<Movie> getById(int id) {
        return repository.getById(id)
                .map(UniversalResponse::new)
                .orElse(new UniversalResponse<>(404, "Movie not found"));
    }

    public UniversalResponse<Movie> save(CreateMovieRequest request) {
        Movie movie = new Movie(request.getTitle(), request.getRating());
        repository.save(movie);
        return new UniversalResponse<>(movie);
    }
}