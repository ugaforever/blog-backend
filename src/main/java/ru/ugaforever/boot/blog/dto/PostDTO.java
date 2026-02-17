package ru.ugaforever.boot.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
    private Long id;
    private String title;
    private String text;
    private List<String> tags;
    private byte[] image;
    private int likesCount;
    private int commentsCount;
}
