package ru.ugaforever.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ugaforever.dto.PostDTO;
import ru.ugaforever.model.Post;
import ru.ugaforever.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private PostDTO convertModelToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setText(post.getText());
        dto.setTags(post.getTags());
        dto.setLikesCount(post.getLikesCount());
        dto.setCommentsCount(post.getCommentsCount());
        return dto;
    }

    // Model → DTO
    public List<PostDTO> findAll() {
        return postRepository.findAll().stream()
                .map(this::convertModelToDTO)
                .collect(Collectors.toList());
    }

    // Model → DTO
    public PostDTO getPostById(Long id) {
        Optional<Post> postOpt = postRepository.findById(id);
        if (postOpt.isEmpty()) {
            throw new RuntimeException("Post not found with id: " + id);
        }

        Post post = postOpt.get();
        return convertModelToDTO(post);
    }

    // DTO -> Model
    public void deleteById(Long id) {
        postRepository.deleteById(id);

    }

    // DTO -> Model
    /*public PostDTO createPost(PostCreateDTO postDTO) {
        Optional<PostDTO> postOpt = postRepository.createPost(postDTO);
        if (postOpt.isEmpty()) {
            throw new RuntimeException("Post was not created");
        }

        return postOpt.get();
    }*/
}

