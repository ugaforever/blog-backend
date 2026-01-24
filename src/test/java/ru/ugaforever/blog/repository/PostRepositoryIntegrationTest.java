package ru.ugaforever.blog.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.configuration.TestPersistenceConfig;
import ru.ugaforever.blog.model.Post;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Интеграционные тесты с реальной БД
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPersistenceConfig.class)
public class PostRepositoryIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void save_ShouldPersistPost() {
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
}
