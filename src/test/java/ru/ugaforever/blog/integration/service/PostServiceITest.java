package ru.ugaforever.blog.integration.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.dto.*;
import ru.ugaforever.blog.integration.configuration.PostServiceTestConfig;
import ru.ugaforever.blog.model.Post;
import ru.ugaforever.blog.service.PostService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PostServiceTestConfig.class)
public class PostServiceITest {

    @Autowired
    private PostService postService;

    @Test
    void testContextLoads() {
        assertThat(postService).isNotNull();
    }

    @Test
    void testSearchPosts_ShouldReturnPageResponseDTO(){
        // Arrange
        SearchRequestDTO request = SearchRequestDTO.builder()
                .search("post")
                .sortBy("title")
                .sortDirection(null)
                .pageNumber(0)
                .pageSize(5)
                .build();

        // Act
        PageResponseDTO<PostDTO> result = postService.searchPosts(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPosts())
                .isNotEmpty()
                .allSatisfy(post -> {
                    assertThat(post.getId()).isNotNull();
                    assertThat(post.getTitle()).isNotBlank();
                    assertThat(post.getText()).isNotBlank();
                    assertThat(post.getLikesCount()).isGreaterThanOrEqualTo(0);
                    assertThat(post.getCommentsCount()).isGreaterThanOrEqualTo(0);
                    assertThat(post.getTags()).isInstanceOf(List.class);
                });
    }

    @Test
    void testCreatePost_ShouldReturnPostDTO(){
        // Arrange
        String expectedTitle ="New post title" + System.currentTimeMillis();
        String expectedText = "New post text" + System.currentTimeMillis();
        String expectedTag1 = "tag_1";
        String expectedTag2 = "tag_2";

        PostCreateDTO request = PostCreateDTO.builder()
                .title(expectedTitle)
                .text(expectedText)
                .tags(List.of(expectedTag1, expectedTag2))
                .build();

        // Act
        PostDTO result = postService.createPost(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(expectedTitle);
        assertThat(result.getText()).isEqualTo(expectedText);
        assertThat(result.getTags().get(0)).isEqualTo(expectedTag1);
        assertThat(result.getTags().get(1)).isEqualTo(expectedTag2);
    }

    @Test
    void testEditPost_ShouldReturnPostDTO(){
        // Arrange
        long expectedId = 1L;
        String expectedTitle ="Edit post title" + System.currentTimeMillis();
        String expectedText = "Edit post text" + System.currentTimeMillis();
        String expectedTag1 = "tag_" + System.currentTimeMillis();
        String expectedTag2 = "tag_" + System.currentTimeMillis();

        PostEditDTO request = PostEditDTO.builder()
                .id(expectedId)
                .title(expectedTitle)
                .text(expectedText)
                .tags(List.of(expectedTag1, expectedTag2))
                .build();

        // Act
        PostDTO result = postService.editPost(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(expectedTitle);
        assertThat(result.getText()).isEqualTo(expectedText);
        assertThat(result.getTags()).contains(expectedTag1);
        assertThat(result.getTags()).contains(expectedTag2);
    }

    @ParameterizedTest
    @ValueSource(longs = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20})
    void testDeleteById(long id) {
        // Arrange
        PostDTO postDTOPreview = postService.getPostById(id);
        assertThat(postDTOPreview).isNotNull();

        // Act
        postService.deleteById(id);

        // Assert
        PostDTO result = postService.getPostById(id);
        assertThat(result).isNull();
    }
}

