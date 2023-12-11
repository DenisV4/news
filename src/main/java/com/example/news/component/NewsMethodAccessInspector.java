package com.example.news.component;

import com.example.news.exception.ResourceNotFoundException;
import com.example.news.model.News;
import com.example.news.service.NewsService;
import com.example.news.web.controller.NewsController;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsMethodAccessInspector extends AbstractMethodAccessInspector<NewsController> {
    private final NewsService newsService;

    @PostConstruct
    public void init() {
        setControllerClass(NewsController.class);
    }

    @Override
    public boolean inspect(Long resourceId, Long userId) {
        News news;
        try {
            news = newsService.findById(resourceId);
        } catch (ResourceNotFoundException e) {
            return true;
        }

        return news.getUser().getId().equals(userId);
    }
}
