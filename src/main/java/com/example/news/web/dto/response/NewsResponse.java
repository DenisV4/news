package com.example.news.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@Setter
public class NewsResponse extends AbstractNewsResponse {

    @Builder.Default
    private List<CommentResponse> comments = new ArrayList<>();
}
