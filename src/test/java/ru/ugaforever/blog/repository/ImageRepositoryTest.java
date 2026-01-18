package ru.ugaforever.blog.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.ugaforever.blog.configuration.DataSourceConfiguration;
import ru.ugaforever.blog.dto.ImageDTO;
import ru.ugaforever.blog.model.Image;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, ImageRepository.class})
@TestPropertySource(locations = "classpath:test-application.properties")
class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Test
    void testFindById() {
        Optional<Image> testImage = imageRepository.findById(0L);

        // Проверяем, что Optional содержит объект
        assertTrue(testImage.isPresent(), "Image should exist for id 0");

        Image image = testImage.get();
        assertEquals(39787, image.getBody().length);
    }

    @Test
    void testShouldAlwaysPass() {
        System.out.println("Тестирование в контексте работает!");
        assertTrue(true);
    }
}