package com.example.news.mapper;

import com.example.news.model.Category;
import com.example.news.web.dto.request.CategoryUpsertRequest;
import com.example.news.web.dto.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category requestToCategory(CategoryUpsertRequest request);

    @Mapping(target = "id", source = "categoryId")
    Category requestToCategory(Long categoryId, CategoryUpsertRequest request);

    CategoryResponse categoryToResponse(Category category);

    List<CategoryResponse> categoryListToCategoryResponseList(List<Category> categories);
}
