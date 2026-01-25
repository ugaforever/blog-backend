package ru.ugaforever.blog.integration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.repository.PostRepository;

import javax.sql.DataSource;

/**
 * простая конфигурация для интеграционных тестов PostRepository
 * рефактор тестов:
 * 1. иерархия конфигураций
 * 2. абстракные классы интеграционных тестов с преднастройкой конфигураций и сканирования бинов
 */

@Configuration
public class PostRepositoryTestConfig {

    @Bean
    public PostRepository postRepository() {
        return new PostRepository(jdbcTemplate(dataSource()), postMapper());
    }

    @Bean
    public PostMapper postMapper() {
        return new PostMapper();
        // Spring найдёт через @ComponentScan
        //return null; // будет заменено
    }

    @Bean
    @Scope("prototype") //новый бин при каждом запросе то есть тесте
    public DataSource dataSource() {

        String threadId = String.valueOf(Thread.currentThread().getId());
        String dbName = "testdb_" + threadId + "_" + System.currentTimeMillis();

        return new EmbeddedDatabaseBuilder()
                .setName(dbName)
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
