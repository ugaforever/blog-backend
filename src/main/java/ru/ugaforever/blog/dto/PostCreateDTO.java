package ru.ugaforever.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PostCreateDTO {

    @NotBlank(message = "Название поста не может быть пустым")
    @Size(min = 1, max = 255, message = "Название поста должно быть от 1 до 255 символов")
    private String title;

    @NotBlank(message = "Текст поста не может быть пустым")
    @Size(min = 1, message = "Текст поста не может быть пустым")
    private String text;

    @NotEmpty(message = "Должен быть указан хотя бы один тег")
    private List<String> tags;
}
