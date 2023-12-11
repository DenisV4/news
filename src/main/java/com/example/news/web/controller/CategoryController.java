package com.example.news.web.controller;

import com.example.news.mapper.CategoryMapper;
import com.example.news.service.CategoryService;
import com.example.news.web.dto.request.CategoryUpsertRequest;
import com.example.news.web.dto.request.Pagination;
import com.example.news.web.dto.response.CategoryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Validated
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryResponse> getAll(@Valid Pagination pagination) {
        var categories = categoryService.findAll(pagination);
        return categoryMapper.categoryListToCategoryResponseList(categories);
    }

    @GetMapping("/{id}")
    public CategoryResponse get(@PathVariable
                                @Positive(message = "ID must be greater than 0")
                                Long id) {

        var category = categoryService.findById(id);
        return categoryMapper.categoryToResponse(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody @Valid CategoryUpsertRequest request) {
        var category = categoryService.save(categoryMapper.requestToCategory(request));
        return categoryMapper.categoryToResponse(category);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable
                                   @Positive(message = "ID must be greater than 0")
                                   Long id,
                                   @Valid @RequestBody CategoryUpsertRequest request) {

        var category = categoryService.update(categoryMapper.requestToCategory(id, request));
        return categoryMapper.categoryToResponse(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable
                       @Positive(message = "ID must be greater than 0")
                       Long id) {

        categoryService.deleteById(id);
    }
}
