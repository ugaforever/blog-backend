package ru.ugaforever.blog.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.ugaforever.blog.dto.CommentDTO;
import ru.ugaforever.blog.model.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CommentMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Comment.builder()
                .id(rs.getLong("id"))
                .text(rs.getString("text"))
                .postId(rs.getLong("post_id"))
                .build();
    }

    public CommentDTO toDTO(Comment post) {
        if (post == null) {
            return null;
        }

        CommentDTO dto = new CommentDTO();
        dto.setId(post.getId());
        dto.setText(post.getText());
        dto.setPostId(post.getPostId());

        return dto;
    }
}
