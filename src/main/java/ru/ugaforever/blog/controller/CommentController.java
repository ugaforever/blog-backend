package ru.ugaforever.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ugaforever.blog.dto.CommentDTO;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{id}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Получить комментарии поста
     *
     * @param id идентификатор поста
     * @return JSON [{"id":1,"text":"Комментарий к посту 1","postId":1},{"id":2,"text":"Ещё один комментарий к посту 1","postId":1}]
     * @apiNote GET: /api/posts/{id}/comments
     */
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable("id") Long id) {

        return ResponseEntity.ok(commentService.getCommentsById(id)); // HTTP 200
    }
}
