package ru.ugaforever.blog.service;

import org.springframework.context.annotation.*;
import ru.ugaforever.blog.configuration.DataSourceConfiguration;

@Configuration
@Import(DataSourceConfiguration.class)
@PropertySource("classpath:application-test.properties")
@ComponentScan("ru.ugaforever.blog.service")
public class TestIntegrationConfig {

}

