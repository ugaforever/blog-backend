package ru.ugaforever;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.ugaforever")
@PropertySource("classpath:application.properties") // Для считывания application.properties
public class WebConfiguration {}
