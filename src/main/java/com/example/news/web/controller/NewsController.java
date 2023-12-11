package com.example.news.web.controller;

import com.example.news.aspect.CreatorOnlyAccess;
import com.example.news.mapper.NewsMapper;
import com.example.news.model.News;
import com.example.news.service.NewsService;
import com.example.news.web.dto.request.NewsFilter;
import com.example.news.web.dto.request.NewsUpsertRequest;
import com.example.news.web.dto.request.Pagination;
import com.example.news.web.dto.response.NewsCutResponse;
import com.example.news.web.dto.response.NewsResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@Validated
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @GetMapping
    public List<NewsCutResponse> getAll(@Valid Pagination pagination) {
        var news = newsService.findAll(pagination);
        return newsMapper.newsListToNewsResponseList(news);
    }

    @GetMapping("/filter")
    public List<NewsCutResponse> filter(@Valid NewsFilter filter) {
        var news = newsService.filterBy(filter);
        return newsMapper.newsListToNewsResponseList(news);
    }

    @GetMapping("/{id}")
    public NewsResponse get(@PathVariable
                            @Positive(message = "ID must be greater than 0")
                            Long id) {

        var news = newsService.findById(id);
        return newsMapper.newsToResponse(news);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponse create(@RequestBody @Valid NewsUpsertRequest request) {
        var news = newsService.save(newsMapper.requestToNews(request));
        return newsMapper.newsToResponse(news);
    }

    @CreatorOnlyAccess
    @PutMapping("/{id}")
    public NewsResponse update(@PathVariable
                               @Positive(message = "ID must be greater than 0")
                               Long id,
                               @Valid @RequestBody NewsUpsertRequest request) {

        var news = newsService.update(newsMapper.requestToNews(id, request));
        return newsMapper.newsToResponse(news);
    }

    @CreatorOnlyAccess
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable
                       @Positive(message = "ID must be greater than 0")
                       Long id) {

        newsService.deleteById(id);
    }
}
