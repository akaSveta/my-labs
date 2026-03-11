package com.goncharova.labs;

import com.goncharova.labs.dto.CreateMovieRequest;
import com.goncharova.labs.dto.UniversalResponse;
import com.goncharova.labs.repository.ConnectionFactory;
import com.goncharova.labs.repository.JdbcMovieRepository;
import com.goncharova.labs.repository.MovieRepository;
import com.goncharova.labs.service.MovieService;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import io.jooby.json.JacksonModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoobyApp extends Jooby {
    private static final Logger log = LoggerFactory.getLogger(JoobyApp.class);

    public JoobyApp() {
        install(new JacksonModule());

        String databaseUrl = System.getenv("DATABASE_URL");
        String databaseUser = System.getenv("DATABASE_USERNAME");
        String databasePassword = System.getenv("DATABASE_PASSWORD");
        String databaseSchema = System.getenv("APP_DATABASE_SCHEMA");

        if (databaseUrl == null || databaseUser == null || databasePassword == null || databaseSchema == null) {
            log.error("Не заданы переменные окружения для подключения к БД!");
            throw new RuntimeException("Не заданы переменные окружения для подключения к БД!");
        }

        ConnectionFactory connectionFactory = new ConnectionFactory(databaseUrl, databaseUser, databasePassword);
        MovieRepository repository = new JdbcMovieRepository(connectionFactory, databaseSchema, "movies");
        MovieService service = new MovieService(repository);

        get("/", ctx -> {
            ctx.setResponseType("application/json");
            return service.getAll();  // Это возвращает UniversalResponse<List<Movie>>
        });

        get("/{id}", ctx -> {
            String id = ctx.path("id").value();
            return service.getById(Integer.parseInt(id));
        });

        post("/", ctx -> {
            CreateMovieRequest request = ctx.body(CreateMovieRequest.class);
            return service.save(request);
        });

        error(Exception.class, (ctx, cause, statusCode) -> {
            log.error("Unexpected error", cause);
            ctx.setResponseType("application/json");
            ctx.setResponseCode(StatusCode.SERVER_ERROR);

            String errorMessage = cause.getMessage();
            if (errorMessage == null) {
                errorMessage = "Unknown error";
            }

            ctx.render(new UniversalResponse<>(5000, errorMessage));
        });

        error(Exception.class, (ctx, cause, statusCode) -> {
            log.error("Unexpected error", cause);
            ctx.setResponseType("application/json");
            ctx.setResponseCode(StatusCode.SERVER_ERROR);
            ctx.render(new UniversalResponse<>(5000, "Неизвестная ошибка: " + cause.getMessage()));
        });
    }
}