package com.example.news.service.impl;

import com.example.news.exception.ResourceNotFoundException;
import com.example.news.model.News;
import com.example.news.repository.NewsRepository;
import com.example.news.repository.NewsSpecification;
import com.example.news.service.NewsService;
import com.example.news.util.BeanUtils;
import com.example.news.web.dto.request.NewsFilter;
import com.example.news.web.dto.request.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Override
    public List<News> filterBy(NewsFilter filter) {
        var newsSpecification = NewsSpecification.withFilter(filter);
        var pageRequest = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
        return newsRepository.findAll(newsSpecification, pageRequest).getContent();
    }

    @Override
    public List<News> findAll(Pagination pagination) {
        var pageRequest = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize());
        return newsRepository.findAll(pageRequest).getContent();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(ResourceNotFoundException.supply("News with id={0} not found", id));
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News update(News news) {
        var existingNews = findById(news.getId());
        BeanUtils.copyNonNullProperties(news, existingNews);
        return newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
