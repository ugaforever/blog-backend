package ru.ugaforever.blog.integration.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.dto.PageResponseDTO;
import ru.ugaforever.blog.dto.PostCreateDTO;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.dto.SearchRequestDTO;
import ru.ugaforever.blog.integration.configuration.PostServiceTestConfig;
import ru.ugaforever.blog.service.PostService;

import java.util.List;

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
        assertThat(result)
                .hasFieldOrPropertyWithValue("hasPrev", false)
                .hasFieldOrPropertyWithValue("hasNext", true);

        assertThat(result)
                .extracting("posts")
                .asInstanceOf(LIST)
                .hasSize(5);
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
}

