package ru.ugaforever.blog.configuration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.ugaforever.blog.service.TestUnitConfig;

/* Обучение
Виды тестов:
1. Unit-тесты ( @ExtendWith(MockitoExtension.class) ) - 80%
2. Интеграционные ( @ExtendWith(SpringExtension.class) + ограниченный контекст) - 15%
3. Полные интеграционные ( Полный контекст + реальная/embedded БД ) - 5%
*/

@Configuration
@ContextConfiguration(classes = DataSourceConfiguration.class)  //тестируем на одной и той же БД H2
@ComponentScan(basePackages = "ru.ugaforever.blog")
@PropertySource("classpath:application.properties")
//TODO разбить TestConfig на 3 конфига: TestPersistenceConfig, TestServiceConfig, TestWebConfig (для реальных проектов, не учебных)
public class TestConfig {

}


