package ru.ugaforever.boot.blog.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ugaforever.boot.blog.dto.PageResponseDTO;
import ru.ugaforever.boot.blog.dto.PostDTO;
import ru.ugaforever.boot.blog.dto.SearchRequestDTO;
import ru.ugaforever.boot.blog.mapper.PostMapper;
import ru.ugaforever.boot.blog.model.Post;
import ru.ugaforever.boot.blog.repository.PostRepository;
import ru.ugaforever.boot.blog.service.PostService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Unit-тесты без контекста Spring
 */

@ExtendWith(MockitoExtension.class) // Только Mockito, без Spring

public class PostServiceUTest {

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
                any(String.class),
                any(String.class),
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

    @Test
    void testGetPostById_ShouldReturnPostDTO(){
        // Arrange
        // Подготовка поста, который вернет репозиторий
        Post mockPost = Post.builder()
                .id(1L).title("Post 1").text("Text 1").likesCount(1).commentsCount(1).build();

        // Подготовка DTO, которые должен вернуть маппер
        PostDTO mockPostDTO = PostDTO.builder()
                .id(1L).title("Post 1").text("Text 1").likesCount(1).commentsCount(1).build();

        doReturn(Optional.of(mockPost)).when(postRepository).findById(anyLong());
        doReturn(mockPostDTO).when(postMapper).toDTO(mockPost);

        // Act
        PostDTO result = postService.getPostById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Post 1");
        assertThat(result.getText()).isEqualTo("Text 1");
        assertThat(result.getLikesCount()).isEqualTo(1);
        assertThat(result.getCommentsCount()).isEqualTo(1);

        System.out.println(result);
    }
}
