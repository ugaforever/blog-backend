package ru.ugaforever.repository;

import org.h2.engine.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ugaforever.model.Post;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcNativePostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcNativePostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findAll() {
        // Выполняем запрос с помощью JdbcTemplate
        // Преобразовываем ответ с помощью RowMapper

        List<Post> posts = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            Post post = new Post();
            post.setId(i);
            post.setTitle("title" + i);
            post.setText("text" + i);
            post.setLikesCount((int) i);
            post.setCommentsCount((int) i);
            posts.add(post);
        }

        return posts;

        /*return jdbcTemplate.query(
                "select id, first_name, last_name, age, active from users",
                (rs, rowNum) -> new Post(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getBoolean("active")
                ));*/
    }

}