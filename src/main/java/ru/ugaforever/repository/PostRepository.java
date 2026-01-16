package ru.ugaforever.repository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.ugaforever.dto.PostDTO;
import ru.ugaforever.model.Post;

import java.util.ArrayList;
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

    // RowMapper для PostDTO
    private final RowMapper<PostDTO> postRowMapper = (rs, rowNum) -> {
        PostDTO post = new PostDTO();
        post.setId(rs.getLong("id"));
        post.setTitle(rs.getString("title"));
        post.setText(rs.getString("text"));

        //iamge
        post.setTags(rs.getString("tags"));
        //post.setComments(rs.getString("comments"));
        post.setLikesCount(rs.getInt("like_count"));

        String json_comments = rs.getString("comments");
        post.setCommentsCount(countElementsWithJackson(json_comments));

        //commentsCount
        /*String strTags = rs.getString("tags");
        if (strTags != null){
            ObjectMapper mapper = new ObjectMapper();

            // Парсинг строки JSON в List<String>
            List<String> tags = null;
            try {
                tags = mapper.readValue(strTags,
                        mapper.getTypeFactory().constructCollectionType(List.class, String.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            post.setTags(tags);
        }*/




        //TODO - complite RowMapper

        /*
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            post.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        post.setPublished(rs.getBoolean("published"));
        post.setViewCount(rs.getInt("view_count"));
        post.setLikeCount(rs.getInt("like_count"));
        post.setCommentCount(rs.getInt("comment_count"));
        post.setCategory(rs.getString("category"));
        post.setImageUrl(rs.getString("image_url"));

        // Получаем теги отдельным запросом
        List<String> tags = getTagsForPost(post.getId());
        post.setTags(tags);*/

        return post;
    };

    private int countElementsWithJackson(String jsonArray) {
        try {
            JsonNode node = objectMapper.readTree(jsonArray);
            return node.isArray() ? node.size() : 0;
        } catch (Exception e) {
            //TODO обработать некорректный JSON
            return 0;
        }
    }

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

    public Optional<PostDTO> findById(Long id) {
        String sql = "SELECT * FROM posts WHERE id = ?";

        try {
            PostDTO post = jdbcTemplate.queryForObject(sql, postRowMapper, id);

            return Optional.ofNullable(post);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}