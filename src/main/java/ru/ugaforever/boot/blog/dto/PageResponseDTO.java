package ru.ugaforever.boot.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponseDTO<T> {
    private List<T> posts;
    //private int pageNumber;
    //private int pageSize;
    //private long totalElements;
    //private int totalPages;
    private boolean hasPrev;
    private boolean hasNext;

    // Фабрика для создания ответа
    public static <T> PageResponseDTO<T> of(List<T> posts,
                                            int pageNumber,
                                            int pageSize,
                                            long totalElements) {

        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return PageResponseDTO.<T>builder()
                .posts(posts)
                .hasPrev(pageNumber != 0)
                .hasNext(pageNumber < totalPages - 1)
                .build();
    }
}