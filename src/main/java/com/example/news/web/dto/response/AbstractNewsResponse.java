package com.example.news.web.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public abstract class AbstractNewsResponse {
    private Long id;

    private CategoryResponse category;

    private UserResponse user;

    private String content;
}
