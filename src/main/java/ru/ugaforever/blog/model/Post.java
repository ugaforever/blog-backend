package ru.ugaforever.blog.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Post {
    private Long id;
    private String title;
    private String text;
    private List<String> tags;
    private int likesCount;
    private int commentsCount;




}
