package com.project.ecommerce.services;

import java.io.IOException;
import java.util.List;

import com.project.ecommerce.dtos.CategoryDto;
import com.project.ecommerce.dtos.PageableResponse;

public interface CategoryService {
  CategoryDto createCategory(CategoryDto categoryDto);

  CategoryDto updateCategoryDto(CategoryDto categoryDto, String categoryId);

  void deleteCategory(String categoryId) throws IOException;

  CategoryDto getCategoryById(String categoryId);

  PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sotDir);

  List<CategoryDto> searchCategory(String keywords);
}
