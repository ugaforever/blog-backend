package ru.ugaforever.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.repository.PostRepository;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)  //интегрирует Spring Test Context с JUnit 5
@ContextConfiguration(classes = TestUnitConfig.class)
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostService postService;

    @BeforeEach
    void resetMocks() {
        reset(postRepository);
    }

/*    @Test
    void testSearchPosts() {
        SearchRequestDTO request = new SearchRequestDTO().builder()
                .search("post")
                .pageNumber(0)
                .pageSize(5)
                .build();

        // Проверка вызова метода save
        doNothing().when(postRepository).search(
                request.getSearch(),
                request.getSortBy(),
                request.getSortDirection(),
                request.getOffset(),
                request.getPageSize()
        );

        // Выполнение метода
        postService.searchPosts(request);

        // Проверка вызовов
        verify(postRepository, times(1)).search(
                request.getSearch(),
                request.getSortBy(),
                request.getSortDirection(),
                request.getOffset(),
                request.getPageSize()
        );
    }*/

/*    @Test
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
    }*/

}