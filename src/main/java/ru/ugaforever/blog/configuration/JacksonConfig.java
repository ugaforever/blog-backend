package ru.ugaforever.blog.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        //mapper.registerModule(new JavaTimeModule());
        // Дополнительные настройки если нужно
        return new ObjectMapper();
    }
}
