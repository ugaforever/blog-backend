package ru.ugaforever.blog.service;

import org.springframework.stereotype.Service;
import ru.ugaforever.blog.dto.CommentDTO;
import ru.ugaforever.blog.repository.CommentRepository;


import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // Model → DTO
    public List<CommentDTO> findAll(Long id) {
        return commentRepository.findAll(id);

    }


    // Model → DTO
/*    public List<CommentDTO> getCommentsById(Long id) {
        return postRepository.getCommentsById(id).stream()
                .map(this::convertModelToDTO)
                .collect(Collectors.toList());
    }*/
}
