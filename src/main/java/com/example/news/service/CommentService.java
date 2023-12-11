package com.example.news.service;

import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.web.dto.request.Pagination;

import java.util.List;

public interface CommentService {

    List<Comment> findAllByNewsId(Long newsId);

    Comment findById(Long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void deleteById(Long id);
}
