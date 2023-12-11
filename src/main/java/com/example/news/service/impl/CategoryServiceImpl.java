package com.example.news.service.impl;

import com.example.news.exception.ResourceNotFoundException;
import com.example.news.model.Category;
import com.example.news.repository.CategoryRepository;
import com.example.news.service.CategoryService;
import com.example.news.util.BeanUtils;
import com.example.news.web.dto.request.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll(Pagination pagination) {
        var pageRequest = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize());
        return categoryRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException.supply("Category with id={0} not found", id));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        var existingCategory = findById(category.getId());
        BeanUtils.copyNonNullProperties(category, existingCategory);
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
