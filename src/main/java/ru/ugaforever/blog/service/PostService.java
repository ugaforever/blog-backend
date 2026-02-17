package ru.ugaforever.blog.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import ru.ugaforever.blog.dto.*;
import ru.ugaforever.blog.mapper.PostMapper;
import ru.ugaforever.blog.model.Post;
import ru.ugaforever.blog.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    // Model → DTO
/*    public List<PostDTO> findAll() {
        return postRepository.findAll().stream()
                .map(this::convertModelToDTO)
                .collect(Collectors.toList());
    }*/

    // Model → DTO
    public PostDTO getPostById(Long id) {
        Optional<Post> postOpt = postRepository.findById(id);
        if (postOpt.isEmpty()) {
            //TODO сейчас HTTP 500
            //throw new RuntimeException("Post not found with id: " + id);
            return null;
        }

        return postMapper.toDTO(postOpt.get());
    }

    // DTO -> Model
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public int addLikeAndGetCount(Long id) {
        return postRepository.addLikeAndGetCount(id);
    }

    public PageResponseDTO<PostDTO> searchPosts(SearchRequestDTO request) {
        // Получаем данные из репозитория
        List<Post> posts;
        long countElements;

        posts = postRepository.search(
                request.getSearch(),
                request.getSortBy(),
                request.getSortDirection(),
                request.getOffset(),
                request.getPageSize()
        );

        countElements = postRepository.countSearch(
                request.getSearch()
        );

        // Конвертируем в DTO
        List<PostDTO> postDTOList = posts.stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());

        // Создаем ответ
        return PageResponseDTO.of(
                postDTOList,
                request.getPageNumber(),
                request.getPageSize(),
                countElements
        );
    }

    public PostDTO createPost(@Valid PostCreateDTO request) {
        Post newPost = postRepository.createAndReturnPost(
                request.getTitle(),
                request.getText(),
                request.getTags()
        );

        return postMapper.toDTO(newPost);
    }

    public PostDTO editPost(@Valid PostEditDTO request) {
        Post editPost = postRepository.editAndReturnPost(
                request.getId(),
                request.getTitle(),
                request.getText(),
                request.getTags()
        );

        return postMapper.toDTO(editPost);
    }
}

