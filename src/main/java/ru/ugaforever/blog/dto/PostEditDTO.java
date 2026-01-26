package ru.ugaforever.blog.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEditDTO {

    @NotNull(message = "ID не может быть пустым")
    @Min(value = 1, message = "ID должен быть положительным числом")
    private Long id;

    @NotBlank(message = "Название поста не может быть пустым")
    @Size(min = 1, max = 255, message = "Название поста должно быть от 1 до 255 символов")
    private String title;

    @NotBlank(message = "Текст поста не может быть пустым")
    @Size(min = 1, message = "Текст поста не может быть пустым")
    private String text;

    @NotEmpty(message = "Должен быть указан хотя бы один тег")
    private List<String> tags;
}
