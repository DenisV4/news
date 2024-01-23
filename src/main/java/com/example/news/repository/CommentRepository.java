package com.example.news.repository;

import com.example.news.model.Comment;
import com.example.news.model.News;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {Comment.Fields.user, Comment.Fields.news})
    List<Comment> findAllByNews(News news);

    @Override
    @EntityGraph(attributePaths = {Comment.Fields.news, Comment.Fields.user})

    @NonNull Optional<Comment> findById(@NonNull Long aLong);
}
