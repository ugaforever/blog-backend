package ru.ugaforever.blog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.model.Post;

//не должно быть зависимостей, нарушение архитектуры
//import ru.ugaforever.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
//@RequiredArgsConstructor
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PostMapper postMapper;

    public PostRepository(JdbcTemplate jdbcTemplate, PostMapper postMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.postMapper = postMapper;
    }

    public List<Post> search(String search,
                             String sortBy,
                             String sortDirection,
                             int offset,
                             int limit) {

        StringBuilder sql = new StringBuilder(
                """
                SELECT
                        posts.id,
                        posts.title as title,
                        posts.text,
                        posts.like_count,
                        (SELECT COUNT(*) FROM comments WHERE post_id = posts.id) as comment_count,
                        (SELECT STRING_AGG(DISTINCT tags.tag, ',') FROM tags WHERE tags.post_id = posts.id) as tags
                FROM posts
                LEFT JOIN tags ON tags.post_id = posts.id
                WHERE 1=1 
                """
        );

        List<Object> params = new ArrayList<>();

        //TODO добавить поиск по тэгам
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (LOWER(title) LIKE LOWER(?))");
            String searchPattern = "%" + search + "%";
            params.add(searchPattern);
        }

        // Добавляем группировку
        sql.append(" GROUP BY posts.id ");

        // Добавляем сортировку
        sql.append(" ORDER BY ")
                .append(validateSortField(sortBy))
                .append(" ")
                .append("DESC".equalsIgnoreCase(sortDirection) ? "DESC" : "ASC");

        // Добавляем пагинацию
        sql.append(" LIMIT ?");
        params.add(limit);
        sql.append(" OFFSET ?");
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), params.toArray(), postMapper); //через объект postMapper
    }

    public long countSearch(String search) {

        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM posts WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (LOWER(title) LIKE LOWER(?))");
            String searchPattern = "%" + search + "%";
            params.add(searchPattern);
        }

        return jdbcTemplate.queryForObject(sql.toString(), Long.class, params.toArray());
    }

    // Методы findAll и countAll (аналогично, но без поиска по тексту)

    private String validateSortField(String field) {
        // Белый список полей для сортировки
        if(field == null) return "title";

        List<String> allowedFields = List.of("id", "title", "text");
        return allowedFields.contains(field) ? field : "title";
    }

    public Optional<Post> findById(Long id) {

        StringBuilder sql = new StringBuilder(
                """
                SELECT
                        posts.id,
                        posts.title as title,
                        posts.text,
                        posts.like_count,
                        (SELECT COUNT(*) FROM comments WHERE post_id = posts.id) as comment_count,
                        (SELECT STRING_AGG(DISTINCT tags.tag, ',') FROM tags WHERE tags.post_id = posts.id) as tags
                FROM posts
                LEFT JOIN tags ON tags.post_id = posts.id
                WHERE posts.id = ? 
                """
        );

        List<Object> params = new ArrayList<>();
        params.add(id);

        List<Post> posts = jdbcTemplate.query(sql.toString(), params.toArray(), postMapper); //через объект postMapper

        // Берём первый элемент если есть
        return posts.isEmpty() ? Optional.empty() : Optional.of(posts.getFirst());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int addLikeAndGetCount(Long id) {
        String updateSql = "UPDATE posts SET like_count = like_count + 1 WHERE id = ?";
        int updatedRows = jdbcTemplate.update(updateSql, id);

        String selectSql = "SELECT like_count FROM posts WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, Integer.class, id);
    }


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