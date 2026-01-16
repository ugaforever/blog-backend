package ru.ugaforever.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.ugaforever.model.Post;

//не должно быть зависимостей, нарушение архитектуры
//import ru.ugaforever.dto.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public PostRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private final RowMapper<Post> postRowMapper = (rs, rowNum) -> {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setTitle(rs.getString("title"));
        post.setText(rs.getString("text"));

        // Парсинг тегов
        //String tagsJson = rs.getString("tags");
        //post.setTags(parseTags(tagsJson));

        // Подсчет комментариев
        String commentsJson = rs.getString("comments");
        post.setCommentsCount(countElementsWithJackson(commentsJson));

        return post;
    };

    private int countElementsWithJackson(String jsonArray) {
        try {
            JsonNode node = objectMapper.readTree(jsonArray);
            return node.isArray() ? node.size() : 0;
        } catch (Exception e) {
            //TODO обработать некорректный JSON
            return -1;
        }
    }

    public List<Post> findAll() {
        String sql = "SELECT * FROM posts ORDER BY id";

        // query() всегда возвращает List (может быть пустым)
        return jdbcTemplate.query(sql, postRowMapper);
    }

    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM posts WHERE id = ?";

        // Используем query() с Optional
        List<Post> posts = jdbcTemplate.query(sql, postRowMapper, id);

        // Берём первый элемент если есть
        return posts.isEmpty() ? Optional.empty() : Optional.of(posts.get(0));
    }

    /*public Optional<PostDTO> createPost(Post post) {

        String sql = "SELECT * FROM posts WHERE id = ?";

        try {
            PostDTO postDTO = jdbcTemplate.queryForObject(sql, postRowMapper, id);

            return Optional.ofNullable(postDTO);
        } catch (Exception e) {
            return Optional.empty();
        }
    }*/
}