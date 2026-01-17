package ru.ugaforever.blog.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ugaforever.blog.model.Comment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//не должно быть зависимостей, нарушение архитектуры
//import ru.ugaforever.dto.*;

@Repository
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public CommentRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Comment> findAll(Long id) {

        String sql = "SELECT comments FROM posts WHERE id = ?";
        String comments = jdbcTemplate.queryForObject(
                sql,
                String.class,
                id
        );

        List<String> texts = parseComments(comments);
        return IntStream.range(0, texts.size())
                .mapToObj(i -> new Comment(
                        (long) (i + 1),
                        texts.get(i),
                        id
                ))
                .collect(Collectors.toList());
    }

    private List<String> parseComments(String jsonComments){
        try {
            return objectMapper.readValue(
                    jsonComments.trim(),
                    new TypeReference<List<String>>() {});
        } catch (Exception e) {
            //TODO обработать некорректный JSON
            return Collections.emptyList();
        }
    }
}
