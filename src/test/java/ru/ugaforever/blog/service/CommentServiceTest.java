package ru.ugaforever.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.repository.CommentRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestIntegrationConfig.class)
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void printDebugInfo() {
        // Проверьте что в базе есть данные
        System.out.println("Total posts in DB: " +
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM posts", Integer.class));
    }

}