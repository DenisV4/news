package com.example.news.web.controller;

import com.example.news.aspect.CreatorOnlyAccess;
import com.example.news.aspect.UserRoleRestriction;
import com.example.news.mapper.CommentMapper;
import com.example.news.security.AppUserDetails;
import com.example.news.service.CommentService;
import com.example.news.web.dto.request.CommentUpsertRequest;
import com.example.news.web.dto.response.CommentResponse;
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
@RequestMapping("/api/comment")
@Validated
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    public List<CommentResponse> getAll(@RequestParam
                                        @Positive(message = "ID must be greater than 0")
                                        Long newsId) {

        var comments = commentService.findAllByNewsId(newsId);
        return commentMapper.commentListToCommentResponseList(comments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    public CommentResponse get(@PathVariable
                               @Positive(message = "ID must be greater than 0")
                               Long id) {

        var comment = commentService.findById(id);
        return commentMapper.commentToResponse(comment);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    public CommentResponse create(@RequestBody @Valid CommentUpsertRequest request,
                                  @AuthenticationPrincipal UserDetails userDetails) {

        var userId = ((AppUserDetails) userDetails).getId();
        var comment = commentService.save(commentMapper.requestToComment(userId, request));
        return commentMapper.commentToResponse(comment);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    @CreatorOnlyAccess
    public CommentResponse update(@PathVariable
                                  @Positive(message = "ID must be greater than 0")
                                  Long id,
                                  @Valid @RequestBody CommentUpsertRequest request,
                                  @AuthenticationPrincipal UserDetails userDetails) {

        var userId = ((AppUserDetails) userDetails).getId();
        var comment = commentService.update(commentMapper.requestToComment(id, userId, request));
        return commentMapper.commentToResponse(comment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    @UserRoleRestriction
    public void delete(@PathVariable
                       @Positive(message = "ID must be greater than 0")
                       Long id,
                       @AuthenticationPrincipal UserDetails userDetails) {

        commentService.deleteById(id);
    }
}
