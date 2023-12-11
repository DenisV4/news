package com.example.news.mapper;

import com.example.news.model.News;
import com.example.news.web.dto.request.NewsUpsertRequest;
import com.example.news.web.dto.response.NewsCutResponse;
import com.example.news.web.dto.response.NewsResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CommentMapper.class, UserMapper.class, CategoryMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(NewsMapperDelegate.class)
public interface NewsMapper {

    News requestToNews(NewsUpsertRequest request);

    @Mapping(target = "id", source = "newsId")
    News requestToNews(Long newsId, NewsUpsertRequest request);

    @Ignore
    NewsResponse newsToResponse(News news);

    @Mapping(target = "commentCount", expression = "java(news.getComments().size())")
    NewsCutResponse newsToCutResponse(News news);

    List<NewsCutResponse> newsListToNewsResponseList(List<News> news);
}
