package ru.ugaforever.blog.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import ru.ugaforever.blog.dto.CommentCreateDTO;
import ru.ugaforever.blog.dto.CommentDTO;
import ru.ugaforever.blog.mapper.CommentMapper;
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

    // Model → DTO
    public List<CommentDTO> getAllByPostId(Long postId) {
        //business-level

        return commentRepository.findAll(postId).stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Model → DTO
    public CommentDTO getCommentByCommentId(Long postId, Long commentId) {
        //business-level

        List<CommentDTO> lstComments = commentRepository.findAll(postId).stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());

        for (CommentDTO commentDTO : lstComments) {
            if (commentDTO.getId() == commentId) {
                return commentDTO;
            }
        }

        //пееделать
        return null;
    }

    public CommentDTO createComment(@Valid CommentCreateDTO request) {

        List<CommentDTO> lstComments = commentRepository.createAndReturnComment(
                request.getText(),
                request.getPostId()
        ).stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());

        for (CommentDTO commentDTO : lstComments) {
            if (commentDTO.getText().equals(request.getText())) {
                return commentDTO;
            }
        }

        return null;
    }

    public void deleteById(Long id, Long commentId) {
        commentRepository.deleteById(id, commentId);
    }
}
