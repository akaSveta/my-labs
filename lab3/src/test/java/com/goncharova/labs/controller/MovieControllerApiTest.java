package com.goncharova.labs.controller;

import com.goncharova.labs.BaseWithPostgresTest;
import com.goncharova.labs.dto.CreateMovieRequest;
import com.goncharova.labs.entity.MovieEntity;
import com.goncharova.labs.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class MovieControllerApiTest extends BaseWithPostgresTest {
    @Autowired
    private MovieRepository repository;
    @Autowired
    private JsonMapper jsonMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testSaveWithInvalidRequest() throws Exception {
        CreateMovieRequest request = new CreateMovieRequest("", "");
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(4001))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void testSaveWithMissingTitle() throws Exception {
        CreateMovieRequest request = new CreateMovieRequest(null, "Message");
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(4001))
                .andExpect(jsonPath("$.message").value("title: Заголовок не может быть пустым"));
    }

    @Test
    void testSaveWithMissingMessage() throws Exception {
        CreateMovieRequest request = new CreateMovieRequest("Title", null);
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(4001))
                .andExpect(jsonPath("$.message").value("message: Сообщение не может быть пустым"));
    }

    @Test
    void testSaveWithTitleTooLong() throws Exception {
        String longTitle = "a".repeat(101);
        CreateMovieRequest request = new CreateMovieRequest(longTitle, "Message");
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(4001))
                .andExpect(jsonPath("$.message").value(containsString("size must be between 1 and 100")));
    }

    @Test
    void testSaveWithMaxTitleLength() throws Exception {
        String maxTitle = "a".repeat(100);
        CreateMovieRequest request = new CreateMovieRequest(maxTitle, "Message");
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.title").value(maxTitle));
    }

    @Test
    void testSaveWithMinTitleLength() throws Exception {
        CreateMovieRequest request = new CreateMovieRequest("a", "Message");
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.title").value("a"));
    }

    @Test
    void testGetAllNews() throws Exception {
        MovieEntity entity1 = MovieEntity.builder()
                .id(UUID.randomUUID())
                .title("Title 1")
                .message("Message 1")
                .build();
        MovieEntity entity2 = MovieEntity.builder()
                .id(UUID.randomUUID())
                .title("Title 2")
                .message("Message 2")
                .build();
        repository.save(entity1);
        repository.save(entity2);
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.movies", hasSize(2)))
                .andExpect(jsonPath("$.data.movies[0].title").value("Title 1"))
                .andExpect(jsonPath("$.data.movies[1].title").value("Title 2"));
    }

    @Test
    void testGetNewsById() throws Exception {
        UUID id = UUID.randomUUID();
        MovieEntity entity = MovieEntity.builder()
                .id(id)
                .title("Title")
                .message("Message")
                .build();
        repository.save(entity);
        mockMvc.perform(get("/movies/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(id.toString()))
                .andExpect(jsonPath("$.data.title").value("Title"));
    }

    @Test
    void testGetNewsByIdNotFound() throws Exception {
        mockMvc.perform(get("/movies/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(4000))
                .andExpect(jsonPath("$.message").value("Не смог найти фильм по id"));
    }
}
