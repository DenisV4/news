package com.example.news.repository;

import com.example.news.model.Category;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.web.dto.request.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {


    static Specification<News> withFilter(NewsFilter filter) {
        return Specification
                .where(byCategoryName(filter.getCategoryName()))
                .and(byUserName(filter.getUserName()));
    }

    static Specification<News> byUserName(String userName) {
        return ((root, query, criteriaBuilder) -> {
            if (userName == null) {
                return null;
            }

            return criteriaBuilder
                    .equal(root.get(News.Fields.user).get(User.Fields.name), userName);
        });
    }

    static Specification<News> byCategoryName(String categoryName) {
        return ((root, query, criteriaBuilder) -> {
            if (categoryName == null) {
                return null;
            }

            return criteriaBuilder
                    .equal(root.get(News.Fields.category).get(Category.Fields.name), categoryName);
        });
    }
}
