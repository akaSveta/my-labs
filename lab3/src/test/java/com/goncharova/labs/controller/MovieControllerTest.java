package com.goncharova.labs.controller;

import com.goncharova.labs.dto.CreateMovieRequest;
import com.goncharova.labs.dto.Movie;
import com.goncharova.labs.dto.MovieList;
import com.goncharova.labs.dto.UniversalResponse;
import com.goncharova.labs.entity.MovieEntity;
import com.goncharova.labs.mapper.MovieMapper;
import com.goncharova.labs.repository.MovieRepository;
import com.goncharova.labs.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes =
        {
                MovieController.class,
                MovieService.class,
                MovieMapper.class
        })
public class MovieControllerTest {
    @MockitoBean
    private MovieRepository repository;
    @Autowired
    private MovieService service;
    @Autowired
    private MovieController controller;

    @Test
    void getAll_Success() {
        UUID id = UUID.randomUUID();
        MovieEntity entity = new MovieEntity(id, "Title", "Message");
        when(repository.findAll()).thenReturn(List.of(entity));
        UniversalResponse<MovieList> actualResponse = controller.getAll();
        assertNotNull(actualResponse);
        MovieList list = actualResponse.getData();
        assertNotNull(list);
        assertEquals(1, list.getMovies().size());
        Movie dto = list.getMovies().iterator().next();
        assertEquals(id, dto.getId());
        assertEquals("Title", dto.getTitle());
        assertEquals("Message", dto.getMessage());
        verify(repository).findAll();
    }

    @Test
    void getById_Success() {
        UUID id = UUID.randomUUID();
        MovieEntity entity = new MovieEntity(id, "Title", "Message");
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        UniversalResponse<Movie> actualResponse = controller.getById(id);
        assertNotNull(actualResponse);
        Movie dto = actualResponse.getData();
        assertEquals(id, dto.getId());
        assertEquals("Title", dto.getTitle());
        assertEquals("Message", dto.getMessage());
        verify(repository).findById(id);
    }

    @Test
    void save_Success() {
        CreateMovieRequest request = new CreateMovieRequest("Title", "Message");
        UUID id = UUID.randomUUID();
        MovieEntity entity = new MovieEntity(id, "Title", "Message");
        when(repository.save(any(MovieEntity.class))).thenReturn(entity);
        UniversalResponse<Movie> actualResponse = controller.save(request);
        assertNotNull(actualResponse);
        Movie dto = actualResponse.getData();
        assertEquals(id, dto.getId());
        assertEquals("Title", dto.getTitle());
        assertEquals("Message", dto.getMessage());
        verify(repository).save(any(MovieEntity.class));
    }
}
