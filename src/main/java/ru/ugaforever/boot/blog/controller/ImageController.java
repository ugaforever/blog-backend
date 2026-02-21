package ru.ugaforever.boot.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.ugaforever.boot.blog.service.ImageService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{id}/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /*@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updatePostImage(
            @PathVariable Long id,
            @RequestParam("image") @Valid @NotNull MultipartFile imageFile) {

        // Валидация файла
        validateImageFile(imageFile);

        // Обновление изображения
        ImageDTO image = imageService.updateImage(id, imageFile);

        return ResponseEntity.ok(image);
    }*/

    private void validateImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Image file cannot be empty");
        }

        // Ограничение размера (например, 5MB)
        long maxSize = 1024 * 1024; // 1MB
        if (file.getSize() > maxSize) {
            throw new RuntimeException("File size exceeds maximum limit of 1MB");
        }

        // Проверка расширения
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1)
                    .toLowerCase();
            List<String> allowedExtensions = Arrays.asList("jpg", "jpeg");
            if (!allowedExtensions.contains(extension)) {
                throw new RuntimeException("File extension not allowed. Allowed: " + allowedExtensions);
            }
        }
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

        return ResponseEntity.ok().build();

        /*ImageDTO image = imageService.getImage(id);

        if (image == null) {
            return ResponseEntity.notFound().build(); // HTTP 404
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image.getBody());*/
    }
}

