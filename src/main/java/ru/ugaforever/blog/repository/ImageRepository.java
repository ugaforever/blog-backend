package ru.ugaforever.blog.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.ugaforever.blog.model.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

//не должно быть зависимостей, нарушение архитектуры
//import ru.ugaforever.dto.*;

@Repository
public class ImageRepository {
    private final Path storagePath;

    public ImageRepository(
            @Value("${app.storage.image}") String storageString) {
        this.storagePath = Paths.get(storageString).toAbsolutePath().normalize();

       try {
            Files.createDirectories(this.storagePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create image directory", e);
        }
    }

    public Optional<Image> findById(Long id) {
        try {
            Path imagePath = storagePath.resolve(id + ".jpg");

            if (!Files.exists(imagePath)) {
                return Optional.empty();
            }

            byte[] imageBytes = Files.readAllBytes(imagePath);

            Image image = new Image();
            image.setBody(imageBytes);

            return Optional.of(image);

        } catch (IOException e) {
            throw new RuntimeException("Error reading image with id: " + id, e);
        }
    }
}
