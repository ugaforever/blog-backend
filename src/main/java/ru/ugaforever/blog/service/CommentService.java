package ru.ugaforever.blog.service;

import org.springframework.stereotype.Service;
import ru.ugaforever.blog.dto.CommentDTO;
import ru.ugaforever.blog.mapper.CommentMapper;
import ru.ugaforever.blog.model.Comment;
import ru.ugaforever.blog.repository.CommentRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    private CommentDTO convertModelToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setPostId(comment.getPostId());
        return dto;
    }

    // Model → DTO
    public List<CommentDTO> getAllCommentsById(Long id) {
        return commentRepository.findAll(id).stream()
                .map(this::convertModelToDTO)
                .collect(Collectors.toList());
    }
}
