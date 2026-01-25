package ru.ugaforever.blog.integration.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ugaforever.blog.integration.configuration.PostServiceTestConfig;
import ru.ugaforever.blog.controller.PostController;
import ru.ugaforever.blog.service.PostService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)  //подключение Spring TestContext Framework для совместимости с JUnit
@ContextConfiguration(classes = PostServiceTestConfig.class)
//@WebAppConfiguration                //тест веб-контекста
class PostControllerITest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostController postController;

/*    @Test
    void testControllerLogic() {
        // Проверяем, что контроллер загружен
        assertNotNull(postController);
    }*/

}