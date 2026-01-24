package ru.ugaforever.blog.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.ugaforever.blog.configuration.DataSourceConfiguration;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, CommentRepository.class})

class CommentRepositoryTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}