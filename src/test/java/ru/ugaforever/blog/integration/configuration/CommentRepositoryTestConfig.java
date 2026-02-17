package ru.ugaforever.blog.integration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.ugaforever.blog.mapper.CommentMapper;
import ru.ugaforever.blog.repository.CommentRepository;


import javax.sql.DataSource;

@Configuration
public class CommentRepositoryTestConfig {
    @Bean
    public CommentRepository commentRepository() {
        return new CommentRepository(jdbcTemplate(dataSource()), commentMapper());
    }

    @Bean
    public CommentMapper commentMapper() {
        return new CommentMapper();
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

