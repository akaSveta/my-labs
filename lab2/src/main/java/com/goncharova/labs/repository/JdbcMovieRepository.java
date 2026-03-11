package com.goncharova.labs.repository;

import com.goncharova.labs.dto.Movie;
import com.goncharova.labs.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMovieRepository implements MovieRepository {

    private final ConnectionFactory connectionFactory;
    private final String schema;
    private final String table;

    public JdbcMovieRepository(ConnectionFactory connectionFactory, String schema, String table) {
        this.connectionFactory = connectionFactory;
        this.schema = '"' + schema + '"';
        this.table = this.schema + "." + '"' + table + '"';
        createTables();
    }

    @Override
    public Optional<Movie> getById(int id) {
        String sql = "SELECT id, title, rating FROM " + table + " WHERE id = ?";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to fetch Movie by id", e);
        }
    }

    @Override
    public List<Movie> getAll() {
        String sql = "SELECT id, title, rating FROM " + table + " ORDER BY title, id";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            List<Movie> result = new ArrayList<>();
            while (rs.next()) {
                result.add(map(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to fetch all Movies", e);
        }
    }

    @Override
    public void save(Movie movie) {
        String sql = "INSERT INTO " + table + " (title, rating) VALUES (?, ?)";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, movie.getTitle());
            ps.setDouble(2, movie.getRating());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    movie.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to save Movie", e);
        }
    }

    private void createTables() {
        String createSchema = "CREATE SCHEMA IF NOT EXISTS " + schema;
        String createTable = "CREATE TABLE IF NOT EXISTS " + table + """
                (
                id SERIAL PRIMARY KEY,
                title TEXT NOT NULL,
                rating DECIMAL(3,1) CHECK (rating >= 0 AND rating <= 10)
                )
                """;
        try (Connection conn = connectionFactory.getConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute(createSchema);
                st.execute(createTable);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to create tables", e);
        }
    }

    private static Movie map(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String title = rs.getString("title");
        Double rating = rs.getDouble("rating");
        return new Movie(id, title, rating);
    }
}