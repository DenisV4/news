package com.example.news.web.controller;

import com.example.news.aspect.CreatorOnlyAccess;
import com.example.news.aspect.UserRoleRestriction;
import com.example.news.mapper.NewsMapper;
import com.example.news.security.AppUserDetails;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    public List<NewsCutResponse> getAll(@Valid Pagination pagination) {
        var news = newsService.findAll(pagination);
        return newsMapper.newsListToNewsResponseList(news);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    public List<NewsCutResponse> filter(@Valid NewsFilter filter) {
        var news = newsService.filterBy(filter);
        return newsMapper.newsListToNewsResponseList(news);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    public NewsResponse get(@PathVariable
                            @Positive(message = "ID must be greater than 0")
                            Long id) {

        var news = newsService.findById(id);
        return newsMapper.newsToResponse(news);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    public NewsResponse create(@RequestBody @Valid NewsUpsertRequest request,
                               @AuthenticationPrincipal UserDetails userDetails) {

        var userId = ((AppUserDetails) userDetails).getId();
        var news = newsService.save(newsMapper.requestToNews(userId, request));
        return newsMapper.newsToResponse(news);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    @CreatorOnlyAccess
    public NewsResponse update(@PathVariable
                               @Positive(message = "ID must be greater than 0")
                               Long id,
                               @Valid @RequestBody NewsUpsertRequest request,
                               @AuthenticationPrincipal UserDetails userDetails) {

        var userId = ((AppUserDetails) userDetails).getId();
        var news = newsService.update(newsMapper.requestToNews(id, userId, request));
        return newsMapper.newsToResponse(news);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    @UserRoleRestriction
    public void delete(@PathVariable
                       @Positive(message = "ID must be greater than 0")
                       Long id,
                       @AuthenticationPrincipal UserDetails userDetails) {

        newsService.deleteById(id);
    }
}
