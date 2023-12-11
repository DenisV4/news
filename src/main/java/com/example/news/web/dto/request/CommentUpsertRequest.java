package com.example.news.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentUpsertRequest {

    @NotNull(message = "News ID is required")
    @Positive(message = "ID must be greater than 0")
    Long newsId;

    @NotNull(message = "User ID is required")
    @Positive(message = "ID must be greater than 0")
    Long userId;

    @NotNull(message = "Text is required")
    @NotBlank(message = "Text should not be blank")
    @Size(min = 10, max = 100, message = "Text length must be less than {min} and greater than {max}")
    String text;
}
