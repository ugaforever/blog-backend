package ru.ugaforever.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.dto.PostListDTO;
import ru.ugaforever.blog.service.PostService;

//не должно быть зависимостей, нарушение архитектуры
//import ru.ugaforever.model.*;

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
    public PostListDTO getPosts(@RequestParam("search") String search,
                                @RequestParam("pageNumber") int pageNumber,
                                @RequestParam("pageSize") int pageSize) {
        PostListDTO listDTO =  new PostListDTO();

        //TODO create findAllbySearch()
        listDTO.setPosts(postService.findAll());
        listDTO.setHasNext(false);
        listDTO.setHasPrev(false);
        listDTO.setLastPage(1);

        return ResponseEntity.ok(listDTO).getBody(); // HTTP 200
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
        PostDTO post = postService.getPostById(id);

        if (post == null) {
            return ResponseEntity.notFound().build(); // HTTP 404
        }

        return ResponseEntity.ok(post); // HTTP 200
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

        /*
      Создать новый пост

      @param title название поста
     * @param text  текст поста (в формате Markdown)
     * @param tags  список тегов (все поля обязательные)
     * @return созданный пост в JSON ({"id":3,"title":"Название поста 3","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"],"likesCount":0,"commentsCount":0})
     * @apiNote POST /api/posts ({"title":"Название поста 3","text":"Текст поста в формате Markdown...","tags":["tag_1","tag_2"]})
     */
    /*@PostMapping("/api/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostCreateDTO postDTO,
                                              UriComponentsBuilder uriBuilder) {
        PostDTO createdPost = postService.createPost(postDTO);

        URI location = uriBuilder
                .path("/{id}")
                .buildAndExpand(createdPost.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdPost);
    }*/
}
