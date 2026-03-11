package com.goncharova.labs.repository;

import com.goncharova.labs.dto.Movie;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMovieRepository implements MovieRepository {
    private final Map<Integer, Movie> storage = new ConcurrentHashMap<>();
    private int nextId = 1;

    @Override
    public Optional<Movie> getById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Movie> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void save(Movie movie) {
        if (movie.getId() == null) {
            movie.setId(nextId++);
        }
        storage.put(movie.getId(), movie);
    }
}