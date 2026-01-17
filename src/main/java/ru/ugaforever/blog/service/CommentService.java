package ru.ugaforever.blog.service;

import org.springframework.stereotype.Service;
import ru.ugaforever.blog.dto.CommentDTO;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.model.Comment;
import ru.ugaforever.blog.model.Post;
import ru.ugaforever.blog.repository.CommentRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    private CommentDTO convertModelToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setPostId(comment.getPostId());
        return dto;
    }

    // Model → DTO
    public List<CommentDTO> getCommentsById(Long id) {
        return commentRepository.findAll(id).stream()
                .map(this::convertModelToDTO)
                .collect(Collectors.toList());
    }
}
