package com.example.news.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewsUpsertRequest {

    @NotNull(message = "Category ID is required")
    @Positive(message = "ID must be greater than 0")
    Long categoryId;

    @NotNull(message = "Content is required")
    @NotBlank(message = "Content should not be blank")
    @Size(min = 3, max = 300, message = "Content length must be less than {max} and greater than {min}")
    String content;
}
