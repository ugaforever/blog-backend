package ru.ugaforever.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ugaforever.blog.dto.CommentDTO;
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
     * Получить все комментарии поста
     *
     * @param id идентификатор поста
     * @return JSON [{"id":1,"text":"Комментарий к посту 1","postId":1},{"id":2,"text":"Ещё один комментарий к посту 1","postId":1}]
     * @apiNote POST: /api/posts/{id}/comments
     */
    //@PostMapping
/*    public ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable("id") Long id) {

        return ResponseEntity.ok(commentService.getAllCommentsById(id)); // HTTP 200
    }*/

    /**
     * Получить комментарий поста
     *
     * @param id идентификатор поста
     * @param commentId идентификатор поста
     * @return JSON {"id":2,"text":"Ещё один комментарий к посту 1","postId":1}
     * @apiNote GET: /api/posts/{id}/comments/{commentId}
     */
/*    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("id") Long id,
                                                 @PathVariable("commentId") Long commentId) {

        List<CommentDTO> lstCommentDTO = commentService.getAllCommentsById(id);
        for(CommentDTO cdto: lstCommentDTO){
            if (cdto.getId() == commentId){
                return ResponseEntity.ok(cdto); // HTTP 200
            }
        }

        return ResponseEntity.notFound().build();
    }*/

}
