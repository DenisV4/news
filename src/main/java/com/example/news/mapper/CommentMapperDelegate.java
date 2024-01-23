package com.example.news.mapper;

import com.example.news.model.Comment;
import com.example.news.service.NewsService;
import com.example.news.service.UserService;
import com.example.news.web.dto.request.CommentUpsertRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Override
    public Comment requestToComment(Long userId, CommentUpsertRequest request) {
        return Comment
                .builder()
                .user(userService.findById(userId))
                .news(newsService.findById(request.getNewsId()))
                .text(request.getText())
                .build();
    }

    @Override
    public Comment requestToComment(Long commentId, Long userId, CommentUpsertRequest request) {
        var comment = requestToComment(userId, request);
        comment.setId(commentId);
        return comment;
    }
}
