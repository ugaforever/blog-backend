package ru.ugaforever.blog.integration.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.integration.configuration.PostRepositoryTestConfig;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.model.Post;
import ru.ugaforever.blog.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Интеграционные тесты с реальной БД
 */


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PostRepositoryTestConfig.class)
public class PostRepositoryITest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Test
    void testContextLoads() {
        assertThat(postRepository).isNotNull();
        assertThat(postMapper).isNotNull();
        System.out.println("Context loaded successfully!");
    }


    @Test
    void testSearch_ShouldReturnPost() {
        // Arrange no

        // Act
        List<Post> result = postRepository.search(
                "post",
                "title",
                "DESC",
                0, 20);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(20);

        // Проверка конкретных полей первого элемента
        assertThat(result.get(0))
                .hasFieldOrPropertyWithValue("id", 9L)
                .hasFieldOrPropertyWithValue("title", "9 post 41")
                .hasFieldOrPropertyWithValue("text", "BeanPostProcessor — возможность динамического изменения бинов.")
                .hasFieldOrPropertyWithValue("likesCount", 51);
    }

    @Test
    void testFindById(){
        // Act
        Optional<Post> result = postRepository.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
                .isPresent() // Проверяем что Optional не пустой
                .hasValueSatisfying(post -> {
                    System.out.println(post);
                    assertThat(post.getId()).isEqualTo(1L);
                    assertThat(post.getTitle()).isEqualTo("1 post 11");
                    assertThat(post.getText()).isEqualTo("Это содержимое первого поста о программировании на Java и Spring Boot.");
                    assertThat(post.getLikesCount()).isEqualTo(5);
                    assertThat(post.getCommentsCount()).isEqualTo(2);
                });
    }
}
