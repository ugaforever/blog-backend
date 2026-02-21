package ru.ugaforever.boot.blog.unit.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.ugaforever.boot.blog.mapper.CommentMapper;
import ru.ugaforever.boot.blog.model.Comment;
import ru.ugaforever.boot.blog.repository.CommentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

/**
 * Unit-тесты без контекста Spring
 */

@ExtendWith(MockitoExtension.class)
public class CommentRepositoryUTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentRepository commentRepository;

    @Test
    void testFindById_ShouldReturnPost(){
        // Arrange
        Long expectedId = 123L;
        String expectedText = "Comment text";
        Long expectedPostId = 456L;

        Comment expectedComment = Comment.builder()
                .id(expectedId)
                .text(expectedText)
                .postId(expectedPostId)
                .build();

        doReturn(List.of(expectedComment)).when(jdbcTemplate).query(
                any(String.class),
                any(Object[].class),
                any(RowMapper.class)
        );

        // Act
        List<Comment> result = commentRepository.findAll(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
                .isNotEmpty()
                .allSatisfy(comment -> {
                    assertThat(comment.getId()).isEqualTo(expectedId);
                    assertThat(comment.getText()).isEqualTo(expectedText);
                    assertThat(comment.getPostId()).isEqualTo(expectedPostId);
                });
    }
}
