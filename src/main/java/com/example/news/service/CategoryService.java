package com.example.news.service;

import com.example.news.model.Category;
import com.example.news.web.dto.request.Pagination;

import java.util.List;

public interface CategoryService {

    List<Category> findAll(Pagination pagination);

    Category findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);
}
