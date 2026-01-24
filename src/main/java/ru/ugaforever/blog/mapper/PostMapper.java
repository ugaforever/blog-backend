package ru.ugaforever.blog.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Post.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .text(rs.getString("text"))
                .tags(parseTagsFromString(rs.getString("tags")))
                .likesCount(rs.getInt("like_count"))
                .commentsCount(rs.getInt("comment_count"))
                .build();
    }

    private List<String> parseTagsFromString(String tagsString) {
        if (tagsString == null || tagsString.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(tagsString.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public PostDTO toDTO(Post post) {
        if (post == null) {
            return null;
        }

        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setText(post.getText());
        dto.setTags(post.getTags());
        dto.setLikesCount(post.getLikesCount());
        dto.setCommentsCount(post.getCommentsCount());

        return dto;
    }

    public Post toEntity(PostDTO dto) {
        if (dto == null) {
            return null;
        }

        return Post.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .text(dto.getText())
                .tags(dto.getTags())
                .commentsCount(dto.getCommentsCount())
                .likesCount(dto.getLikesCount())
                .build();
    }

/*    public RowMapper<Post> rowMapper = (rs, rowNum) -> Post.builder()
            .id(rs.getLong("id"))
            .title(rs.getString("title"))
            .text(rs.getString("text"))
            .tags(parseTagsFromString(rs.getString("tags")))
            .likesCount(rs.getInt("like_count"))
            .commentsCount(rs.getInt("comment_count"))
            .build();*/


}