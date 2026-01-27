package ru.ugaforever.blog.integration.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.dto.CommentDTO;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.dto.PostEditDTO;
import ru.ugaforever.blog.integration.configuration.CommentServiceTestConfig;
import ru.ugaforever.blog.service.CommentService;
import ru.ugaforever.blog.service.PostService;

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
        List<CommentDTO> result = commentService.getAllCommentsById(id);

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

}
