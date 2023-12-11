package com.example.news.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryUpsertRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name should not be blank")
    @Size(min = 1, max = 30, message = "Name length must be less than {min} and greater than {max}")
    String name;
}
