package ru.ugaforever.boot.blog.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data  // Lombok генерирует геттеры/сеттеры
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDTO {
    private String search;                  // поисковой запрос
    @Builder.Default
    private String sortBy = "title";        // поле для сортировки
    @Builder.Default
    private String sortDirection = "DESC";  // направление сортировки

    @Builder.Default
    @Min(value = 0, message = "Номер страницы должен быть >= 0")
    private Integer pageNumber = 0;

    @Min(value = 0, message = "Размер страницы должен быть >= 0")
    @Max(value = 10, message = "Размер страницы должен быть <= 10")
    @Builder.Default
    private Integer pageSize = 5;

    // Получение смещения (offset)
    public int getOffset() {
        return pageNumber * pageSize;
    }

    public boolean hasSearch() {
        return search != null && !search.trim().isEmpty();
    }
}
