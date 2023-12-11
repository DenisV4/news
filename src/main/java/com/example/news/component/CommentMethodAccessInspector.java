package com.example.news.component;

import com.example.news.exception.ResourceNotFoundException;
import com.example.news.model.Comment;
import com.example.news.service.CommentService;
import com.example.news.web.controller.CommentController;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMethodAccessInspector extends AbstractMethodAccessInspector<CommentController> {
    private final CommentService commentService;

    @PostConstruct
    public void init() {
        setControllerClass(CommentController.class);
    }

    @Override
    public boolean inspect(Long resourceId, Long userId) {
        Comment comment;
        try {
            comment = commentService.findById(resourceId);
        } catch (ResourceNotFoundException e) {
            return true;
        }

        return comment.getUser().getId().equals(userId);
    }
}
