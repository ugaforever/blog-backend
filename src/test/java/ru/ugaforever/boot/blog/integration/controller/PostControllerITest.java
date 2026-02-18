package ru.ugaforever.boot.blog.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ugaforever.boot.blog.controller.PostController;
import ru.ugaforever.boot.blog.dto.PostCreateDTO;
import ru.ugaforever.boot.blog.dto.PostDTO;
import ru.ugaforever.boot.blog.service.PostService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)  // Загружает только web-слой
class PostControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    @Test
    void getPosts() {
    }

    @Test
    void getPost() throws Exception {
        // Arrange
        Long postId = 1L;
        String postTitle = "Post 1";
        String postText = "Text 1";

        PostDTO postDTO = PostDTO.builder().id(1L).title(postTitle).text(postText).likesCount(1).commentsCount(1).build();

        when(postService.getPostById(postId)).thenReturn(postDTO);

        // Act & Assert
        mockMvc.perform(get("/api/posts/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value(postTitle))
                .andExpect(jsonPath("$.text").value(postText))
                .andExpect(jsonPath("$.likesCount").value(1))
                .andExpect(jsonPath("$.commentsCount").value(1));

        // Verify
        verify(postService, times(1)).getPostById(postId);
    }

    @Test
    void incrementLikes() throws Exception {
        // Arrange
        Long postId = 1L;
        int expectedCount = 5;

        when(postService.addLikeAndGetCount(postId)).thenReturn(expectedCount);

        // Act & Assert
        mockMvc.perform(post("/api/posts/{id}/likes", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedCount));

        // Verify
        verify(postService, times(1)).addLikeAndGetCount(postId);
    }

    @Test
    void createPost() throws Exception {
        // Arrange
        String postTitle = "Post 1";
        String postText = "Text 1";

        PostCreateDTO postCreateDTO = PostCreateDTO.builder().title(postTitle).text(postText).tags(List.of("tag1")).build();
        String postCreateDTOJson = objectMapper.writeValueAsString(postCreateDTO);
        PostDTO postDTO = PostDTO.builder().id(1L).title(postTitle).text(postText).likesCount(0).commentsCount(0).build();



        when(postService.createPost(postCreateDTO)).thenReturn(postDTO);

        // Act & Assert
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postCreateDTOJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(postTitle))
                .andExpect(jsonPath("$.text").value(postText))
                .andExpect(jsonPath("$.likesCount").value(0))
                .andExpect(jsonPath("$.commentsCount").value(0));

        // Verify
        verify(postService, times(1)).createPost(postCreateDTO);
    }
}