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
    public List<CommentDTO> getAllByPostId(Long postId) {
        //business-level

        return commentRepository.findAll(postId).stream()
                .map(this::convertModelToDTO)
                .collect(Collectors.toList());
    }

    // Model → DTO
    public CommentDTO getCommentByCommentId(Long postId, Long commentId) {
        //business-level

        List<CommentDTO> lstComments = commentRepository.findAll(postId).stream()
                .map(this::convertModelToDTO)
                .collect(Collectors.toList());

        for(CommentDTO commentDTO : lstComments){
            if (commentDTO.getId() == commentId){
                return commentDTO;
            }
        }

        return null;
    }

}
