package com.goncharova.labs.service;

import com.goncharova.labs.dto.CreateMovieRequest;
import com.goncharova.labs.dto.Movie;
import com.goncharova.labs.dto.MovieList;
import com.goncharova.labs.dto.UniversalResponse;
import com.goncharova.labs.entity.MovieEntity;
import com.goncharova.labs.exceptions.NotFoundExceptions;
import com.goncharova.labs.mapper.MovieMapper;
import com.goncharova.labs.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceUnitTest {
    @Mock
    private MovieRepository repository;
    @Mock
    private MovieMapper mapper;
    @InjectMocks
    private MovieService service;

    @Test
    void getById_Success() {
        UUID id = UUID.randomUUID();
        MovieEntity entity = new MovieEntity(id, "Title", "Message");
        Movie dto = new Movie(id, "Title", "Message");
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);
        UniversalResponse<Movie> response = service.getById(id);
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("SUCCESS", response.getMessage());
        assertEquals(dto, response.getData());
        verify(repository).findById(id);
        verify(mapper).toDto(entity);
    }

    @Test
    void getById_NotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        NotFoundExceptions exception = assertThrows(NotFoundExceptions.class, () -> service.getById(id));
        assertEquals("Не смог найти фильм по id", exception.getMessage());
        verify(repository).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void getAll_Success() {
        MovieEntity entity = new MovieEntity(UUID.randomUUID(), "Title", "Message");
        Movie dto = new Movie(entity.getId(), "Title", "Message");
        List<MovieEntity> entities = List.of(entity);
        List<Movie> dtos = List.of(dto);
        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(dtos);
        UniversalResponse<MovieList> response = service.getAll();
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(1, response.getData().getMovies().size());
        assertTrue(response.getData().getMovies().contains(dto));
        verify(repository).findAll();
        verify(mapper).toDtoList(entities);
    }

    @Test
    void save_Success() {
        CreateMovieRequest request = new CreateMovieRequest("New Title", "New Message");
        MovieEntity savedEntity = MovieEntity.builder()
                .id(UUID.randomUUID())
                .title(request.getTitle())
                .message(request.getMessage())
                .build();
        Movie dto = new Movie(savedEntity.getId(), savedEntity.getTitle(), savedEntity.getMessage());
        when(repository.save(any(MovieEntity.class))).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(dto);
        UniversalResponse<Movie> response = service.save(request);
        assertNotNull(response);
        assertEquals(dto, response.getData());
        verify(repository).save(argThat(entity ->
                entity.getTitle().equals(request.getTitle()) &&
                        entity.getMessage().equals(request.getMessage()) &&
                        entity.getId() != null
        ));
        verify(mapper).toDto(savedEntity);
    }

    @Test
    void update_Success() {
        UUID id = UUID.randomUUID();
        CreateMovieRequest request = new CreateMovieRequest("Updated Title", "Updated Message");
        MovieEntity existingEntity = new MovieEntity(id, "Old Title", "Old Message");
        MovieEntity updatedEntity = new MovieEntity(id, "Updated Title", "Updated Message");
        Movie updatedDto = new Movie(id, "Updated Title", "Updated Message");
        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(MovieEntity.class))).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(updatedDto);
        UniversalResponse<Movie> response = service.update(id, request);
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("SUCCESS", response.getMessage());
        assertEquals("Updated Title", response.getData().getTitle());
        assertEquals("Updated Message", response.getData().getMessage());
        verify(repository).findById(id);
        verify(repository).save(existingEntity);
        verify(mapper).toDto(updatedEntity);
    }

    @Test
    void update_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        CreateMovieRequest request = new CreateMovieRequest("Title", "Message");
        when(repository.findById(id)).thenReturn(Optional.empty());
        NotFoundExceptions exception = assertThrows(NotFoundExceptions.class,
                () -> service.update(id, request));
        assertEquals("Не смог найти фильм по id для обновления", exception.getMessage());
        verify(repository).findById(id);
        verify(repository, never()).save(any());
        verifyNoInteractions(mapper);
    }

    @Test
    void deleteById_Success() {
        UUID id = UUID.randomUUID();
        MovieEntity existingEntity = new MovieEntity(id, "Title", "Message");
        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        doNothing().when(repository).deleteById(id);
        UniversalResponse<Void> response = service.deleteById(id);
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("Запись успешно удалена", response.getMessage());
        verify(repository).findById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void deleteById_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        NotFoundExceptions exception = assertThrows(NotFoundExceptions.class,
                () -> service.deleteById(id));
        assertEquals("Не смог найти фильм по id для удаления", exception.getMessage());
        verify(repository).findById(id);
        verify(repository, never()).deleteById(any());
    }
}
