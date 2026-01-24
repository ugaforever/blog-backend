package ru.ugaforever.blog.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.ugaforever.blog.WebConfiguration;
import ru.ugaforever.blog.configuration.TestConfig;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)  //подключение Spring TestContext Framework для совместимости с JUnit
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration                //тест веб-контекста
class PostControllerTest {

    @Autowired
    private PostController postController;

    @Test
    void testControllerLogic() {
        // Проверяем, что контроллер загружен
        assertNotNull(postController);
    }

}