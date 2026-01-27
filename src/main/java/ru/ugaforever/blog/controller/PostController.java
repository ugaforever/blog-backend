package ru.ugaforever.blog.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ugaforever.blog.dto.*;
import ru.ugaforever.blog.service.PostService;

//не должно быть зависимостей, нарушение архитектуры
//import ru.ugaforever.model.*;


@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Получить список всех постов
     *
     * @param search     строка из поля поиска
     * @param pageNumber номер текущей страницы
     * @param pageSize   число постов на странице (все поля обязательные)
     * @return JSON {"posts":[{"id":1,"title":"Название поста 1","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"],"likesCount":5,"commentsCount":1},{"id":2,"title":"Название поста 2","text":"Текст поста в формате Markdown...","tags":[],"likesCount":1,"commentsCount":5}],"hasPrev":true,"hasNext":false,"lastPage":3}
     * @apiNote GET: /api/posts?search=post&pageNumber=0&pageSize=5
     */
    @GetMapping("/posts")
    public ResponseEntity<PageResponseDTO<PostDTO>> getPosts(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {

        // Создаем DTO запроса
        SearchRequestDTO request = new SearchRequestDTO().builder()
                .search(search)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();

        //лимит размера 1 страницы
        if (request.getPageSize() > 10) {
            request.setPageSize(10);
        }

        // Получаем результат
        PageResponseDTO<PostDTO> response = postService.searchPosts(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Получить пост
     *
     * @param id идентификатор поста
     * @return JSON {"id":1,"title":"Название поста 1","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"],"likesCount":5,"commentsCount":1}
     * @apiNote GET: /api/posts/{id}
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("id") Long id) {
        PostDTO postDTO = postService.getPostById(id);

        if (postDTO == null) {
            return ResponseEntity.notFound().build(); // HTTP 404
        }

        return ResponseEntity.ok(postDTO); // HTTP 200
    }

    /**
     * Удалить пост
     *
     * @param id идентификатор поста
     * @return 200 Ok
     * @apiNote DELETE: /api/posts/{id}
     */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity deletePost(@PathVariable(name = "id") Long id) {
        postService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Инкремент числа лайков поста
     *
     * @param id идентификатор поста
     * @return 200 Ok, обновлённое число лайков поста (число в теле ответа)
     * @apiNote POST: /api/posts/{id}/likes
     */
    @PostMapping("/posts/{id}/likes")
    public ResponseEntity<Integer> incrementLikes(@PathVariable(name = "id") Long id) {
        int likesCount = postService.addLikeAndGetCount(id);

        // Возвращаем новое количество лайков
        return ResponseEntity.ok(likesCount);
    }

    /**
     * Создать новый пост
     *
     * @return созданный пост в JSON ({"id":3,"title":"Название поста 3","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"],"likesCount":0,"commentsCount":0})
     * @apiNote POST /api/posts ({"title":"Название поста 3","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"]})
     *
     * curl -X POST http://localhost:8080/api/posts -H "Content-Type: application/json" -d '{"title":"new post 3","text": "Новый пост.","tags": ["tag_1", "tag_2"]}'
     */
    @PostMapping("/posts")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostCreateDTO request) {

        PostDTO response = postService.createPost(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Редактировть пост
     *
     * @return отредактированный пост в JSON ({"id":3,"title":"Название поста 3","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"],"likesCount":0,"commentsCount":0})
     * @apiNote PUT /api/posts ({"title":"Название поста 3","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"]})
     *
     * curl -X PUT http://localhost:8080/api/posts/1 -H "Content-Type: application/json" -d '{"id": "1","title": "edit title","text": "Ред Текст","tags": ["tag_3", "tag_4"]}'
     */
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDTO> editPost( @Valid @RequestBody PostEditDTO request) {

        PostDTO response = postService.editPost(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
