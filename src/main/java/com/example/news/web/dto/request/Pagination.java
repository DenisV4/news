package com.example.news.web.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class Pagination {

    @NotNull(message = "Page number is required")
    @PositiveOrZero(message = "Page number must be greater than or equal to 0")
    private Integer pageNumber;

    @NotNull(message = "Page size is required")
    @PositiveOrZero(message = "Page size must be greater than or equal to 0")
    private Integer pageSize;
}
