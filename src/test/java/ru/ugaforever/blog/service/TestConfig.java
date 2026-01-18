package ru.ugaforever.blog.service;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.ugaforever.blog.repository.CommentRepository;
import ru.ugaforever.blog.repository.ImageRepository;
import ru.ugaforever.blog.repository.PostRepository;

@Configuration
@ComponentScan("ru.ugaforever.blog.service")
public class TestConfig {

    @Bean
    @Primary
    public PostRepository mockPostRepository() {
        return Mockito.mock(PostRepository.class);

    }

    @Bean
    @Primary
    public CommentRepository mockCommentRepository() {
        return Mockito.mock(CommentRepository.class);
    }

    @Bean
    @Primary
    public ImageRepository mockImageRepository() {
        return Mockito.mock(ImageRepository.class);
    }

}