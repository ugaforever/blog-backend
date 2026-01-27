package ru.ugaforever.blog.integration.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.repository.PostRepository;
import ru.ugaforever.blog.service.PostService;

@Configuration
@Import(PostRepositoryTestConfig.class)
public class PostServiceTestConfig {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Bean
    public PostService postService() {
        return new PostService(postRepository, postMapper);
    }
}
