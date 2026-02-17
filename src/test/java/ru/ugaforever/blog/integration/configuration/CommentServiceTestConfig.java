package ru.ugaforever.blog.integration.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ugaforever.blog.mapper.CommentMapper;
import ru.ugaforever.blog.repository.CommentRepository;
import ru.ugaforever.blog.service.CommentService;

@Configuration
@Import(CommentRepositoryTestConfig.class)
public class CommentServiceTestConfig {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Bean
    public CommentService commentService() {
        return new CommentService(commentRepository, commentMapper);
    }
}
