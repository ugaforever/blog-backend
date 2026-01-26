package ru.ugaforever.blog.unit.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.model.Post;
import ru.ugaforever.blog.repository.PostRepository;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

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

    @Test
    void testCreateAndReturnPost(){
        // Arrange
        Long expectedId = 123L;
        String expectedTitle = "New Post";
        String expectedText = "New Text";
        Post expectedNewPost = Post.builder()
                .id(expectedId)
                .title(expectedTitle)
                .text(expectedText)
                .tags(List.of("tag_1", "tag_2"))
                .build();

        // Mock JdbcTemplate.update с KeyHolder
        doAnswer(invocation -> {
            // Получаем KeyHolder (2-й аргумент)
            KeyHolder keyHolder = invocation.getArgument(1);

            // Создаем список ключей как делает Spring
            List<Map<String, Object>> keyList = new ArrayList<>();
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("GENERATED_KEY", expectedId);
            keyList.add(keyMap);

            // Используем рефлексию для установки в GeneratedKeyHolder
            if (keyHolder instanceof GeneratedKeyHolder) {
                try {
                    Field keyListField = GeneratedKeyHolder.class.getDeclaredField("keyList");
                    keyListField.setAccessible(true);
                    keyListField.set(keyHolder, keyList);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to set key in KeyHolder", e);
                }
            }

            return 1;
        }).when(jdbcTemplate).update(
                any(PreparedStatementCreator.class),
                any(KeyHolder.class)
        );

        // Mock для findById
        doReturn(List.of(expectedNewPost)).when(jdbcTemplate).query(
                any(String.class),
                any(Object[].class),
                any(RowMapper.class)
        );

        // Act
        Post result = postRepository.createAndReturnPost(
                expectedTitle,
                expectedText,
                List.of("tag1", "tag2")
        );

        // Assert
        System.out.println(result);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(expectedId);
        assertThat(result.getTitle()).isEqualTo(expectedTitle);
        assertThat(result.getText()).isEqualTo(expectedText);
        assertThat(result.getTags().get(0)).isEqualTo("tag_1");
        assertThat(result.getTags().get(1)).isEqualTo("tag_2");
    }

    @Test
    void testFindById_ShouldReturnPost(){
        // Arrange
        Post expectedPost = Post.builder().id(1L).title("Post 1").text("Text 1").likesCount(1).build();

        doReturn(List.of(expectedPost)).when(jdbcTemplate).query(
                any(String.class),
                any(Object[].class),
                any(RowMapper.class)
        );

        // Act
        Optional<Post> result = postRepository.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
                .isPresent() // Проверяем что Optional не пустой
                .hasValueSatisfying(post -> {
                    assertThat(post.getId()).isEqualTo(1L);
                    assertThat(post.getTitle()).isEqualTo("Post 1");
                    assertThat(post.getText()).isEqualTo("Text 1");
                });
    }
}
