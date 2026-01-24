package ru.ugaforever.blog.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ugaforever.blog.dto.PageResponseDTO;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.dto.SearchRequestDTO;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.model.Post;
import ru.ugaforever.blog.repository.PostRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Unit-тесты без контекста Spring
 */

@ExtendWith(MockitoExtension.class)
public class PostServiceUnitTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    @Test
    void testSearchPosts_ShouldReturnPageResponseDTO() {
        // Arrange
        SearchRequestDTO request = SearchRequestDTO.builder()
                .search("post")
                .pageSize(5)
                .pageNumber(0)
                .build();

        // Подготовка постов, которые вернет репозиторий
        List<Post> mockPostsFromRepository = List.of(
                Post.builder().id(1L).title("Post 1").text("Text 1").likesCount(1).commentsCount(1).build(),
                Post.builder().id(2L).title("Post 2").text("Text 2").likesCount(2).commentsCount(2).build()
        );
        // Подготовка DTO, которые должен вернуть маппер
        List<PostDTO> mockDTOsFromMapper = List.of(
                PostDTO.builder().id(1L).title("Post 1").text("Text 1").likesCount(1).commentsCount(1).build(),
                PostDTO.builder().id(2L).title("Post 2").text("Text 2").likesCount(2).commentsCount(2).build()
        );

        doReturn(mockPostsFromRepository).when(postRepository).search(
                any(String.class),
                isNull(),
                isNull(),
                eq(0),
                anyInt()
        );

        when(postRepository.countSearch(
                any(String.class)
        )).thenReturn(20L);

        when(postMapper.toDTO(mockPostsFromRepository.get(0))).thenReturn(mockDTOsFromMapper.get(0));
        when(postMapper.toDTO(mockPostsFromRepository.get(1))).thenReturn(mockDTOsFromMapper.get(1));

        // Act
        PageResponseDTO<PostDTO> result;

        result = postService.searchPosts(request);

        // Assert
        assertThat(result).isNotNull();
        System.out.println(result);

        // Проверяем, что DTO не null
        assertThat(result.getPosts())
                .hasSize(2)
                .doesNotContainNull();

        // Проверяем конкретные поля
        assertThat(result.getPosts().get(0).getTitle()).isEqualTo("Post 1");
        assertThat(result.getPosts().get(1).getTitle()).isEqualTo("Post 2");

        // Проверяем пагинацию
        assertThat(result.isHasPrev()).isEqualTo(false);
        assertThat(result.isHasNext()).isEqualTo(true);
    }
}
