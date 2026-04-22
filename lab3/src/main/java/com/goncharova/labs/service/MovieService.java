package com.goncharova.labs.service;

import com.goncharova.labs.dto.CreateMovieRequest;
import com.goncharova.labs.dto.Movie;
import com.goncharova.labs.dto.MovieList;
import com.goncharova.labs.dto.UniversalResponse;
import com.goncharova.labs.entity.MovieEntity;
import com.goncharova.labs.exceptions.NotFoundExceptions;
import com.goncharova.labs.mapper.MovieMapper;
import com.goncharova.labs.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final MovieMapper mapper;

    public MovieService(MovieRepository repository, MovieMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UniversalResponse<Movie> getById(UUID id) {
        MovieEntity movie = repository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Не смог найти фильм по id"));
        Movie movieDto = mapper.toDto(movie);
        return new UniversalResponse<>(movieDto);
    }

    public UniversalResponse<MovieList> getAll() {
        List<MovieEntity> movies = repository.findAll();
        List<Movie> movieDtoList = mapper.toDtoList(movies);
        MovieList movieList = new MovieList(movieDtoList);
        return new UniversalResponse<>(movieList);
    }

    public UniversalResponse<Movie> save(CreateMovieRequest request) {
        MovieEntity entity = new MovieEntity();
        java.util.UUID id = java.util.UUID.randomUUID();
        entity.id = id;
        entity.title = request.getTitle();
        entity.message = request.getMessage();
        MovieEntity savedEntity = repository.save(entity);
        Movie movieDto = mapper.toDto(savedEntity);
        return new UniversalResponse<>(movieDto);
    }

    public UniversalResponse<Movie> update(UUID id, CreateMovieRequest request) {
        MovieEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Не смог найти фильм по id для обновления"));
        existingEntity.setTitle(request.getTitle());
        existingEntity.setMessage(request.getMessage());
        MovieEntity updatedEntity = repository.save(existingEntity);
        Movie movieDto = mapper.toDto(updatedEntity);
        return new UniversalResponse<>(movieDto);
    }

    public UniversalResponse<Void> deleteById(UUID id) {
        MovieEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Не смог найти фильм по id для удаления"));
        repository.deleteById(id);
        return new UniversalResponse<>(200, "Запись успешно удалена");
    }
}
