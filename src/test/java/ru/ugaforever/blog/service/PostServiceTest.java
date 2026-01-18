package ru.ugaforever.blog.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.dto.ImageDTO;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.model.Post;
import ru.ugaforever.blog.repository.PostRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
     void testGetPostById(){
        long testId = 1;
        String testTitle = "test title";
        String testText = "test title";
        Post testPost = new Post();
        testPost.setId(testId);
        testPost.setText(testText);
        testPost.setTitle(testTitle);

        when(postRepository.findById(testId)).thenReturn(Optional.of(testPost));

        PostDTO result = postService.getPostById(testId);

        assertEquals(testId, result.getId());
        assertEquals(testText, result.getText());
        assertEquals(testTitle, result.getTitle());
    }

}