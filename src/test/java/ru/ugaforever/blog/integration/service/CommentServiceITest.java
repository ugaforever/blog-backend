package ru.ugaforever.blog.integration.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.dto.CommentDTO;
import ru.ugaforever.blog.integration.configuration.CommentServiceTestConfig;
import ru.ugaforever.blog.service.CommentService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommentServiceTestConfig.class)
public class CommentServiceITest {
    @Autowired
    private CommentService commentService;

    @Test
    void testContextLoads() {
        assertThat(commentService).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(longs = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20})
    void testGetAllCommentsById(long id){
        // Arrange

        // Act
        List<CommentDTO> result = commentService.getAllByPostId(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
                .allSatisfy(comment -> {
                    assertThat(comment).isInstanceOf(CommentDTO.class);
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
    void testGetAllCommentsById(long postId, long commentId){
        // Arrange

        // Act
        CommentDTO result = commentService.getCommentByCommentId(postId, commentId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getText()).isNotNull();
        assertThat(result.getPostId()).isNotNull();

        assertThat(result.getId()).isInstanceOf(Long.class);
        assertThat(result.getText()).isInstanceOf(String.class);
        assertThat(result.getPostId()).isInstanceOf(Long.class);

        assertThat(result.getId()).isPositive();
        assertThat(result.getText()).isNotBlank();
        assertThat(result.getPostId()).isPositive();
    }
}
