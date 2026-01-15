package ru.ugaforever.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ugaforever.dto.PostDTO;
import ru.ugaforever.model.Post;
import ru.ugaforever.service.PostService;

import java.util.ArrayList;
import java.util.List;

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
     * @apiNote GET: /api/posts?search=Lalala&pageNumber=1&pageSize=5
     */
    @GetMapping("/posts")
    public List<Post> getUsers() {
        return postService.findAll();
    }

    /**
     * Получить пост
     *
     * @param id идентификатор поста
     * @return JSON {"id":1,"title":"Название поста 1","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"],"likesCount":5,"commentsCount":1}
     * @apiNote GET: /api/posts/{id}
     */
    // GET /api/posts/{id}
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("id") Long id) {
        PostDTO post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    /**
     * Создать новый пост
     *
     * @param title название поста
     * @param text  текст поста (в формате Markdown)
     * @param tags  список тегов (все поля обязательные)
     * @return созданный пост в JSON ({"id":3,"title":"Название поста 3","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"],"likesCount":0,"commentsCount":0})
     * @apiNote POST /api/posts ({"title":"Название поста 3","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"]})
     */
    @PostMapping("/api/posts")
    //public ResponseEntity<PostDTO> createPost(
    public void createPost() {
    }
/*
            @Parameter(title = "Данные поста", required = true)
            @Valid @RequestBody PostDTO postDTO,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetails userDetails) {

        PostDTO createdPost = postService.createPost(postDTO);
        */
/*URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPost.getId())
                .toUri();
*//*

        return ResponseEntity.created(location).body(createdPost);
*/


}
