package ru.ugaforever.blog.mapper;
import org.springframework.stereotype.Component;
import ru.ugaforever.blog.dto.ImageDTO;
import ru.ugaforever.blog.model.Image;

import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ImageMapper  {
    public ImageDTO toDTO(Image image) {
        if (image == null) {
            return null;
        }

        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setBody(image.getBody());

        return dto;
    }

    public Image toModel(ImageDTO dto) {
        if (dto == null) {
            return null;
        }

        Image image = new Image();
        image.setId(dto.getId());
        image.setBody(dto.getBody());

        return image;
    }
}
