package ru.ugaforever.blog.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.ugaforever.blog.mapper.CommentMapper;
import ru.ugaforever.blog.model.Comment;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public List<Comment> createAndReturnComment(String text,
                                          Long postId) {

        String sql = "INSERT INTO comments (text, post_id) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, text);
            ps.setLong(2, postId);
            return ps;
        }, keyHolder);

        // Получаем сгенерированный ID
        Long generatedId = keyHolder.getKey().longValue();

        // Получаем полную запись по ID
        return findAll(postId);
    }

    public void deleteById(Long id, Long commentId) {
        String sql = "DELETE FROM comments WHERE (post_id = ?) AND (id = ?)";
        jdbcTemplate.update(sql, id, commentId);
    }
}
