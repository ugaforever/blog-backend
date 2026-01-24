package ru.ugaforever.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;


@Data                   // Генерирует геттеры, сеттеры, equals, hashCode, toString
@Builder                // Генерирует builder
@NoArgsConstructor      // Конструктор без параметров (требуется для JPA/Jackson)
@AllArgsConstructor     // Конструктор со ВСЕМИ параметрами (требуется для @Builder)
public class PostDTO {
    private Long id;
    private String title;
    private String text;
    private List<String> tags;
    private byte[] image;
    private int likesCount;
    private int commentsCount;
}
