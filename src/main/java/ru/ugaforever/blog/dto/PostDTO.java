package ru.ugaforever.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
