package com.example.news.mapper;

import com.example.news.model.Comment;
import com.example.news.web.dto.request.CommentUpsertRequest;
import com.example.news.web.dto.response.CommentResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(CommentMapperDelegate.class)
public interface CommentMapper {

    Comment requestToComment(CommentUpsertRequest request);

    @Mapping(target = "id", source = "commentId")
    Comment requestToComment(Long commentId, CommentUpsertRequest request);

    @Mapping(target = "newsContent", expression = "java(comment.getNews().getContent())")
    CommentResponse commentToResponse(Comment comment);

    List<CommentResponse> commentListToCommentResponseList(List<Comment> comments);
}
