package ru.ugaforever.blog.configuration;

import org.springframework.context.annotation.*;
import ru.ugaforever.blog.mapper.PostMapper;

/* Обучение
Виды тестов:
1. Unit-тесты ( @ExtendWith(MockitoExtension.class) ) - 80%
2. Интеграционные ( @ExtendWith(SpringExtension.class) + ограниченный контекст) - 15%
3. Полные интеграционные ( Полный контекст + реальная/embedded БД ) - 5%
*/


@Configuration
@Import(DataSourceConfiguration.class)
@ComponentScan(basePackages = {
        "ru.ugaforever.blog.repository",
        "ru.ugaforever.blog.entity"
})
@PropertySource("classpath:application.properties") //сейчас только один дата-сет
public class TestPersistenceConfig {
    // Бины для работы с данными
    @Bean
    public PostMapper postMapper() {
        return new PostMapper();    //! НЕ Mock !
    }
}
