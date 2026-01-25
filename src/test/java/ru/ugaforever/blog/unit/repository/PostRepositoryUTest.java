package ru.ugaforever.blog.unit.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.model.Post;
import ru.ugaforever.blog.repository.PostRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Unit-тесты без контекста Spring
 */

@ExtendWith(MockitoExtension.class)
public class PostRepositoryUTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostRepository postRepository;

    @Test
    void testSearch_ShouldReturnListPost() {
        // Arrange
        List<Post> expectedPosts = List.of(
                Post.builder().id(1L).title("Post 1").text("Text 1").likesCount(1).build(),
                Post.builder().id(2L).title("Post 2").text("Text 2").likesCount(2).build()
        );

        doReturn(expectedPosts).when(jdbcTemplate).query(
                any(String.class),
                any(Object[].class),
                any(RowMapper.class)
        );

        // Act
        List<Post> result = postRepository.search(
                "post",
                "title",
                "DESC",
                0, 20);


        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);

        // 3. Проверяем все поля по порядку
        assertThat(result)
                .extracting(
                        Post::getId,
                        Post::getTitle,
                        Post::getText,
                        Post::getLikesCount
                )
                .containsExactly(
                        tuple(1L, "Post 1", "Text 1", 1),
                        tuple(2L, "Post 2", "Text 2", 2)
                );

        verify(jdbcTemplate, times(1)).query(
                any(String.class),
                any(Object[].class),
                any(RowMapper.class));
    }
}
