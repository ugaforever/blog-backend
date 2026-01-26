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
        String expectedSearch = "post";
        String expectedSortBy = "title";
        String expectedSortDirection = "DESC";

        // Act
        List<Post> result = postRepository.search(
                expectedSearch,
                expectedSortBy,
                expectedSortDirection,
                0, 20);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(20);

        // Assert - общие проверки
        assertThat(result)
                .isNotNull()
                .allSatisfy(post -> {
                    // Проверка что все элементы не null
                    assertThat(post).isNotNull();
                    assertThat(post.getId()).isInstanceOf(Long.class);
                    assertThat(post.getTitle()).isInstanceOf(String.class);
                    assertThat(post.getText()).isInstanceOf(String.class);

                    // Проверка ненулевых значений
                    assertThat(post.getId()).isPositive();
                    assertThat(post.getTitle()).isNotBlank();
                    assertThat(post.getText()).isNotBlank();
                    assertThat(post.getLikesCount()).isGreaterThanOrEqualTo(0);
                    assertThat(post.getCommentsCount()).isGreaterThanOrEqualTo(0);

                    // Проверка что title содержит поисковый запрос
                    assertThat(post.getTitle().toLowerCase())
                            .contains(expectedSearch);
                });
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

    @Test
    void testCreateAndReturnPost() {
        // Arrange no
        String expectedTitle ="New post title" + System.currentTimeMillis();
        String expectedText = "New post text" + System.currentTimeMillis();
        String expectedTag1 = "tag_1";
        String expectedTag2 = "tag_2";

        // Act
        Post result = postRepository.createAndReturnPost(
                expectedTitle,
                expectedText,
                List.of(expectedTag1, expectedTag2)
        );

        // Assert
        System.out.println(result);
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(expectedTitle);
        assertThat(result.getText()).isEqualTo(expectedText);
        assertThat(result.getTags().get(0)).isEqualTo(expectedTag1);
        assertThat(result.getTags().get(1)).isEqualTo(expectedTag2);
    }

}
