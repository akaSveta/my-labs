package com.goncharova.labs.controller;

import com.goncharova.labs.dto.CreateMovieRequest;
import com.goncharova.labs.dto.Movie;
import com.goncharova.labs.dto.MovieList;
import com.goncharova.labs.dto.UniversalResponse;
import com.goncharova.labs.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "Movies operations")
public class MovieController {
    private static final Logger log = LoggerFactory.getLogger(MovieController.class);
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all news")
    @ApiResponse(responseCode = "200", description = "List of news", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = UniversalResponse.class)
    ))
    public UniversalResponse<MovieList> getAll() {
        log.info("Request to get all news");
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movies by id")
    @ApiResponse(responseCode = "200", description = "Movies found", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = UniversalResponse.class)
    ))
    @ApiResponse(responseCode = "404", description = "Movies not found", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = UniversalResponse.class)
    ))
    public UniversalResponse<Movie> getById(@PathVariable UUID id) {
        log.info("Request to get movies by id: {}", id);
        return service.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create movies")
    @ApiResponse(responseCode = "200", description = "Movies created", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = UniversalResponse.class)
    ))
    public UniversalResponse<Movie> save(@Valid @RequestBody CreateMovieRequest request) {
        log.info("Request to save movies: {}", request);
        return service.save(request);
    }
}

