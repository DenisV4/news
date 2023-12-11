package com.example.news.web.controller;

import com.example.news.aspect.CreatorOnlyAccess;
import com.example.news.mapper.CommentMapper;
import com.example.news.service.CommentService;
import com.example.news.web.dto.request.CommentUpsertRequest;
import com.example.news.web.dto.response.CommentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@Validated
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping
    public List<CommentResponse> getAll(@RequestParam
                                        @Positive(message = "ID must be greater than 0")
                                        Long newsId) {

        var comments = commentService.findAllByNewsId(newsId);
        return commentMapper.commentListToCommentResponseList(comments);
    }

    @GetMapping("/{id}")
    public CommentResponse get(@PathVariable
                               @Positive(message = "ID must be greater than 0")
                               Long id) {

        var comment = commentService.findById(id);
        return commentMapper.commentToResponse(comment);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse create(@RequestBody @Valid CommentUpsertRequest request) {
        var comment = commentService.save(commentMapper.requestToComment(request));
        return commentMapper.commentToResponse(comment);
    }

    @CreatorOnlyAccess
    @PutMapping("/{id}")
    public CommentResponse update(@PathVariable
                                  @Positive(message = "ID must be greater than 0")
                                  Long id,
                                  @Valid @RequestBody CommentUpsertRequest request) {

        var comment = commentService.update(commentMapper.requestToComment(id, request));
        return commentMapper.commentToResponse(comment);
    }

    @CreatorOnlyAccess
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable
                       @Positive(message = "ID must be greater than 0")
                       Long id) {

        commentService.deleteById(id);
    }
}
