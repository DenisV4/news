package com.example.news.mapper;

import com.example.news.model.News;
import com.example.news.service.CategoryService;
import com.example.news.service.UserService;
import com.example.news.web.dto.request.NewsUpsertRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Override
    public News requestToNews(Long userId, NewsUpsertRequest request) {
        return News
                .builder()
                .category(categoryService.findById(request.getCategoryId()))
                .user(userService.findById(userId))
                .content(request.getContent())
                .build();
    }

    @Override
    public News requestToNews(Long newsId, Long userId, NewsUpsertRequest request) {
        var news = requestToNews(userId, request);
        news.setId(newsId);
        return news;
    }
}
