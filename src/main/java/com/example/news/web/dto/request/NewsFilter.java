package com.example.news.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewsFilter extends Pagination {

    @NotBlank(message = "Category name should not be blank")
    @Size(min = 1, max = 30, message = "Category name length must be less than {min} and greater than {max}")
    private final String categoryName;

    @NotBlank(message = "User name should not be blank")
    @Size(min = 3, max = 30, message = "User name length must be less than {min} and greater than {max}")
    private final String userName;
}
