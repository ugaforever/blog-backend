package ru.ugaforever.blog.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ugaforever.blog.dto.CommentDTO;

import java.util.Collections;
import java.util.List;

@Repository
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public CommentRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public List<CommentDTO> findAll(Long id) {

        String sql = "SELECT comments FROM posts WHERE id = ?";

        // query() всегда возвращает List (может быть пустым)
        //return jdbcTemplate.query(sql, postRowMapper);

        return Collections.emptyList();

    }
}
