package com.example.news.service.impl;

import com.example.news.exception.ResourceNotFoundException;
import com.example.news.model.Comment;
import com.example.news.repository.CommentRepository;
import com.example.news.service.CommentService;
import com.example.news.service.NewsService;
import com.example.news.service.UserService;
import com.example.news.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NewsService newsService;

    @Override
    public List<Comment> findAllByNewsId(Long newsId) {
        var news = newsService.findById(newsId);
        return commentRepository.findAllByNews(news);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException.supply("Comment with id={0} not found", id));
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        var user = userService.findById(comment.getUser().getId());
        var news = newsService.findById(comment.getNews().getId());

        var existingComment = findById(comment.getId());
        BeanUtils.copyNonNullProperties(comment, existingComment);
        existingComment.setUser(user);
        existingComment.setNews(news);

        return commentRepository.save(existingComment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
