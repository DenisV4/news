package com.example.news.service;

import com.example.news.model.News;
import com.example.news.web.dto.request.NewsFilter;
import com.example.news.web.dto.request.Pagination;

import java.util.List;

public interface NewsService {

    List<News> filterBy(NewsFilter filter);

    List<News> findAll(Pagination pagination);

    News findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);
}
