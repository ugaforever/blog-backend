package ru.ugaforever.blog.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ru.ugaforever.blog.mapper.ImageMapper;

import ru.ugaforever.blog.model.Image;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

//не должно быть зависимостей, нарушение архитектуры
//import ru.ugaforever.dto.*;

@Repository
public class ImageRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ImageMapper imageMapper;

    public ImageRepository(JdbcTemplate jdbcTemplate, ImageMapper imageMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.imageMapper = imageMapper;
    }

    public Optional<Image> findById(Long id) {
        String sql = "SELECT image FROM posts WHERE id = ?";

        try {
            Blob imageBlob = jdbcTemplate.queryForObject(
                    sql,
                    Blob.class,
                    id
            );

            if (imageBlob == null) {
                return Optional.empty();
            }

            Image image = new Image();
            image.setId(id);

            try {
                // Получаем байты из BLOB
                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                image.setBody(imageData);

            } catch (SQLException e) {
                return Optional.empty();
            }

            return Optional.of(image); // ✅ Используем Optional.of() (не ofNullable)

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    public Optional<Image> updateImage(Long id, MultipartFile imageFile) {

        try {
            String sql = "UPDATE posts SET image = ? WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(
                    sql,
                    imageFile.getBytes(),
                    id
            );
            if (rowsAffected == 0) {
                return Optional.empty();
            }
        }catch(IOException e){
            return Optional.empty();
        }

        return findById(id);
    }
}
