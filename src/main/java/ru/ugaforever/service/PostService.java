package ru.ugaforever.service;

import org.springframework.stereotype.Service;
import ru.ugaforever.model.Post;
import ru.ugaforever.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }
}

