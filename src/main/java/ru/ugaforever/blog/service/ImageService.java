package ru.ugaforever.blog.service;

import org.springframework.stereotype.Service;
import ru.ugaforever.blog.dto.ImageDTO;
import ru.ugaforever.blog.dto.PostDTO;
import ru.ugaforever.blog.model.Image;
import ru.ugaforever.blog.model.Post;
import ru.ugaforever.blog.repository.ImageRepository;
import ru.ugaforever.blog.repository.PostRepository;

import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    // Model → DTO
    public ImageDTO getImage(Long id) {
        Optional<Image> imageOpt = imageRepository.findById(id);
        if (imageOpt.isEmpty()) {
            throw new RuntimeException("Image for post id: " + id + " not found");
        }

        Image image = imageOpt.get();

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setBody(image.getBody());

        return imageDTO;
    }
}
