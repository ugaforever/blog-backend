package ru.ugaforever.blog.integration.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.dto.PageResponseDTO;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.dto.SearchRequestDTO;
import ru.ugaforever.blog.integration.configuration.PostServiceTestConfig;
import ru.ugaforever.blog.service.PostService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PostServiceTestConfig.class)
public class PostServiceITest {

    @Autowired
    private PostService postService;
    /*@Autowired
    private PostRepository postRepository;*/

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

        System.out.println(result);
    }
}

