package ru.ugaforever.blog.unit.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.model.Post;
import ru.ugaforever.blog.repository.PostRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PostMapperUTest {

    @InjectMocks
    private PostMapper postMapper;


    @Test
    void testToDTO() {
        // Arrange
        Long expectedId = 1L;
        String expectedTitle ="post title";
        String expectedText = "post text";
        String expectedTag1 = "tag_1";
        String expectedTag2 = "tag_2";
        int expectedlikesCount = 100;
        int expectedCommentsCount = 200;

        Post post = Post.builder()
                .id(expectedId)
                .title(expectedTitle)
                .text(expectedText)
                .tags(List.of(expectedTag1, expectedTag2))
                .likesCount(expectedlikesCount)
                .commentsCount(expectedCommentsCount)
                .build();

        PostDTO result = postMapper.toDTO(post);

        // Assert
        System.out.println(result);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(expectedId);
        assertThat(result.getTitle()).isEqualTo(expectedTitle);
        assertThat(result.getText()).isEqualTo(expectedText);
        assertThat(result.getTags().get(0)).isEqualTo(expectedTag1);
        assertThat(result.getTags().get(1)).isEqualTo(expectedTag2);
        assertThat(result.getLikesCount()).isEqualTo(expectedlikesCount);
        assertThat(result.getCommentsCount()).isEqualTo(expectedCommentsCount);
    }

    @Test
    void testToModel() {
        // Arrange
        Long expectedId = 1L;
        String expectedTitle ="post title";
        String expectedText = "post text";
        String expectedTag1 = "tag_1";
        String expectedTag2 = "tag_2";
        int expectedlikesCount = 100;
        int expectedCommentsCount = 200;

        PostDTO postDTO = PostDTO.builder()
                .id(expectedId)
                .title(expectedTitle)
                .text(expectedText)
                .tags(List.of(expectedTag1, expectedTag2))
                .likesCount(expectedlikesCount)
                .commentsCount(expectedCommentsCount)
                .build();

        Post result = postMapper.toModel(postDTO);

        // Assert
        System.out.println(result);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(expectedId);
        assertThat(result.getTitle()).isEqualTo(expectedTitle);
        assertThat(result.getText()).isEqualTo(expectedText);
        assertThat(result.getTags().get(0)).isEqualTo(expectedTag1);
        assertThat(result.getTags().get(1)).isEqualTo(expectedTag2);
        assertThat(result.getLikesCount()).isEqualTo(expectedlikesCount);
        assertThat(result.getCommentsCount()).isEqualTo(expectedCommentsCount);

    }
}