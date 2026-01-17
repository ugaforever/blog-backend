package ru.ugaforever.blog.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ugaforever.blog.dto.ImageDTO;
import ru.ugaforever.blog.service.ImageService;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/posts/{id}/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Получить картинку поста
     *
     * @param id идентификатор поста
     * @return массив байт картинки поста в теле ответа
     * @apiNote GET: /api/posts/{id}/image
     */
    @GetMapping
    public ResponseEntity<?> getImage(@PathVariable("id") Long id) {
        ImageDTO image = imageService.getImage(id);

        if (image == null) {
            return ResponseEntity.notFound().build(); // HTTP 404
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image.getBody());
    }
}

