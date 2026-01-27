package ru.ugaforever.blog.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Image {
    private long id;
    private byte[] body;
}
