package ru.ugaforever.blog.integration.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.integration.configuration.CommentRepositoryTestConfig;
import ru.ugaforever.blog.mapper.CommentMapper;
import ru.ugaforever.blog.model.Comment;
import ru.ugaforever.blog.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommentRepositoryTestConfig.class)
public class CommentRepositoryITest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Test
    void testContextLoads() {
        assertThat(commentRepository).isNotNull();
        assertThat(commentMapper).isNotNull();
        System.out.println("Context loaded successfully!");
    }

    @ParameterizedTest
    @ValueSource(longs = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20})
    void testFindById(long id){
        // Act
        List<Comment> result = commentRepository.findAll(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
                .allSatisfy(comment -> {
                    assertThat(comment).isInstanceOf(Comment.class);

                    // Проверка что все элементы не null
                    assertThat(comment).isNotNull();
                    assertThat(comment.getId()).isInstanceOf(Long.class);
                    assertThat(comment.getText()).isInstanceOf(String.class);
                    assertThat(comment.getPostId()).isInstanceOf(Long.class);

                    // Проверка ненулевых значений
                    assertThat(comment.getId()).isPositive();
                    assertThat(comment.getPostId()).isPositive();
                    assertThat(comment.getText()).isNotBlank();
                });
    }
    @Test
    void testCreateComment() {
        // Arrange no
        String expectedText = "New comment title" + System.currentTimeMillis();
        long expectedPostId = 1L;

        // Act
        List<Comment> result = commentRepository.createAndReturnComment(
                expectedText,
                expectedPostId
        );

        // Assert
        assertThat(result).isNotNull();

        // Ищем комментарий содержащий определенный текст
        Optional<Comment> newComment = result.stream()
                .filter(comment -> comment.getText().contains(expectedText))
                .findFirst();

        assertThat(newComment)
                .isPresent()
                .get()
                .satisfies(comment -> {
                    assertThat(comment.getText()).containsIgnoringCase(expectedText);
                    assertThat(comment.getId()).isGreaterThanOrEqualTo(0);
                    assertThat(comment.getPostId()).isGreaterThanOrEqualTo(0);
                });
    }

    @ParameterizedTest
    @CsvSource({
            "1,	1", "1,	2",
            "2,	3", "2,	4", "2,	5",
            "3,	6", "3,	7", "3,	8",
            "4,	9",
            "5,	10", "5, 11", "5, 12",
            "6,	13",
            "7,	14",
            "8,	15",
            "9,	16",
            "10, 17",
            "11, 18", "11, 19",
            "12, 20",
            "13, 21",
            "14, 22",
            "15, 23", "15, 24",
            "16, 25", "16, 26",
            "17, 27",
            "18, 28",
            "18, 29", "18, 30", "18, 31",
            "19, 32",
            "20, 33", "20, 34", "20, 35",
    })
    void testDeleteById(long postId, long commentId) {
        // Arrange
        List<Comment> comments = commentRepository.findAll(postId);
        assertThat(comments)
                .anySatisfy(comment -> {
                    assertThat(comment.getId()).isEqualTo(commentId);
                });

        // Act
        commentRepository.deleteById(postId, commentId);

        // Assert
        List<Comment> result = commentRepository.findAll(postId);
        assertThat(result)
                .noneMatch(comment -> (comment.getId().equals(commentId)));
    }

}
