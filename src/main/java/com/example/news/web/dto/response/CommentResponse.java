package com.example.news.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CommentResponse {

    private Long id;

    private String text;

    private String newsContent;

    private UserResponse user;
}
