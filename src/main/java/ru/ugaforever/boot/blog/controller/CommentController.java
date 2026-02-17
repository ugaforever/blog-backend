package ru.ugaforever.boot.blog.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ugaforever.boot.blog.dto.CommentCreateDTO;
import ru.ugaforever.boot.blog.dto.CommentDTO;
import ru.ugaforever.boot.blog.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{id}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Получить все комментарии поста
     *
     * @param id идентификатор поста
     * @return JSON [{"id":1,"text":"Комментарий к посту 1","postId":1},{"id":2,"text":"Ещё один комментарий к посту 1","postId":1}]
     * @apiNote GET: /api/posts/{id}/comments
     */
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable("id") Long id) {

        return ResponseEntity.ok(commentService.getAllByPostId(id)); // HTTP 200
    }

    /**
     * Получить комментарий поста
     *
     * @param id идентификатор поста
     * @param commentId идентификатор поста
     * @return JSON {"id":2,"text":"Ещё один комментарий к посту 1","postId":1}
     * @apiNote GET: /api/posts/{id}/comments/{commentId}
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("id") Long id,
                                                 @PathVariable("commentId") Long commentId) {

        CommentDTO commentDTO = commentService.getCommentByCommentId(id, commentId);

        //переделать
        if (commentDTO == null){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(commentDTO); // HTTP 200
        }
    }


    /**
     * Добавить комментарий к посту
     *
     * @param id идентификатор поста
     * @return JSON [{"id":1,"text":"Комментарий к посту 1","postId":1},{"id":2,"text":"Ещё один комментарий к посту 1","postId":1}]
     * @apiNote POST: /api/posts/{id}/comments
     */
    @PostMapping
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable("id") Long id,
            @Valid @RequestBody CommentCreateDTO request) {

        CommentDTO response = commentService.createComment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Удалить комментарйи
     *
     * @param id идентификатор поста
     * @param commentId идентификатор комментайия
     * @return 200 Ok
     * @apiNote DELETE: /api/posts/{id}/comments/{commentId}
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable(name = "id") Long id,
                                        @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteById(id, commentId);
        return ResponseEntity.ok().build();
    }

}
