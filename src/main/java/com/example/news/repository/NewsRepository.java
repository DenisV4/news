package com.example.news.repository;

import com.example.news.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    @Override
    @EntityGraph(attributePaths = {News.Fields.category, News.Fields.user, News.Fields.comments})
    Page<News> findAll(Specification<News> spec, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {News.Fields.category, News.Fields.user, News.Fields.comments})
    Page<News> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {News.Fields.category, News.Fields.user, News.Fields.comments})
    Optional<News> findById(Long id);
}
