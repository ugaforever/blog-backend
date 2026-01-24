package ru.ugaforever.blog.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Import(TestServiceConfig.class) // Наследуем Service
@ComponentScan(basePackages = "ru.ugaforever.blog.controller")
@EnableWebMvc // ТОЛЬКО здесь
public class TestControllerConfig {
    // Веб-бины
}
