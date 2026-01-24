package ru.ugaforever.blog.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TestPersistenceConfig.class) // Наследуем Persistence
@ComponentScan(basePackages = "ru.ugaforever.blog.service")
public class TestServiceConfig {
    // Дополнительные бины для сервисов
}
