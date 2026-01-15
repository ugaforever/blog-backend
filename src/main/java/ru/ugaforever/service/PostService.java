package ru.ugaforever.service;

import org.springframework.stereotype.Service;
import ru.ugaforever.dto.PostDTO;
import ru.ugaforever.model.Post;
import ru.ugaforever.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public PostDTO getPostById(Long id) {
        Optional<PostDTO> postOpt = postRepository.findById(id);
        if (postOpt.isEmpty()) {
            throw new RuntimeException("Post not found with id: " + id);
        }

        PostDTO post = postOpt.get();

        // Получаем комментарии
        //List<CommentDTO> comments = commentRepository.findByPostId(id);
        //post.setComments(comments);

        return post;
    }

    /*public PostDTO createPost(PostDTO postDTO) {
    }*/
}

