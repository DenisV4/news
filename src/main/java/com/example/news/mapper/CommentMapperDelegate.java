package com.example.news.mapper;

import com.example.news.model.Comment;
import com.example.news.service.NewsService;
import com.example.news.service.UserService;
import com.example.news.web.dto.request.CommentUpsertRequest;
import com.example.news.web.dto.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Override
    public Comment requestToComment(CommentUpsertRequest request) {
        return Comment
                .builder()
                .user(userService.findById(request.getUserId()))
                .news(newsService.findById(request.getNewsId()))
                .text(request.getText())
                .build();
    }

    @Override
    public Comment requestToComment(Long commentId, CommentUpsertRequest request) {
        var comment = requestToComment(request);
        comment.setId(commentId);
        return comment;
    }
}
