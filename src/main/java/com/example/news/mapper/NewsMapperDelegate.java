package com.example.news.mapper;

import com.example.news.model.News;
import com.example.news.service.CategoryService;
import com.example.news.service.UserService;
import com.example.news.web.dto.request.NewsUpsertRequest;
import com.example.news.web.dto.response.NewsCutResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Override
    public News requestToNews(NewsUpsertRequest request) {
        return News
                .builder()
                .category(categoryService.findById(request.getCategoryId()))
                .user(userService.findById(request.getUserId()))
                .content(request.getContent())
                .build();
    }

    @Override
    public News requestToNews(Long newsId, NewsUpsertRequest request) {
        var news = requestToNews(request);
        news.setId(newsId);
        return news;
    }
}
