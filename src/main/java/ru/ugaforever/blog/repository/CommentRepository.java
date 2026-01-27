package ru.ugaforever.blog.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ugaforever.blog.mapper.CommentMapper;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.model.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//не должно быть зависимостей, нарушение архитектуры
//import ru.ugaforever.dto.*;

@Repository
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CommentMapper commentMapper;

    public CommentRepository(JdbcTemplate jdbcTemplate, CommentMapper commentMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.commentMapper = commentMapper;
    }

    public List<Comment> findAll(Long id) {


        StringBuilder sql = new StringBuilder("SELECT id, text, post_id FROM comments WHERE post_id = ?");

        List<Object> params = new ArrayList<>();
        params.add(id);


        return jdbcTemplate.query(sql.toString(), params.toArray(), commentMapper);
    }

/*    private List<String> parseComments(String jsonComments){
        try {
            return objectMapper.readValue(
                    jsonComments.trim(),
                    new TypeReference<List<String>>() {});
        } catch (Exception e) {
            //TODO обработать некорректный JSON
            return Collections.emptyList();
        }
    }*/
}
