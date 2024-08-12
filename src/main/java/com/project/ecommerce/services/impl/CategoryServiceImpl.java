package com.project.ecommerce.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.ecommerce.dtos.CategoryDto;
import com.project.ecommerce.dtos.PageableResponse;
import com.project.ecommerce.entities.Category;
import com.project.ecommerce.exceptions.ResourceNotFoundException;
import com.project.ecommerce.helper.Helper;
import com.project.ecommerce.repositories.CategoryRepository;
import com.project.ecommerce.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;

  @Value("${category.profile.image.path}")
  private String imageUploadPath;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public CategoryDto createCategory(CategoryDto categoryDto) {

    String category_id = UUID.randomUUID().toString();
    categoryDto.setCategoryId(category_id);
    Category category = modelMapper.map(categoryDto, Category.class);

    Category category2 = categoryRepository.save(category);
    CategoryDto categoryDto2 = modelMapper.map(category2, CategoryDto.class);
    return categoryDto2;

  }

  @Override
  public CategoryDto updateCategoryDto(CategoryDto categoryDto, String categoryId) {
    Category category1 = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category Not Found By Id Exception"));

    category1.setCoverImage(categoryDto.getCoverImage());
    category1.setDescription(categoryDto.getDescription());
    category1.setTitle(categoryDto.getTitle());

    Category category2 = categoryRepository.save(category1);
    return modelMapper.map(category2, CategoryDto.class);

  }

  @Override
  public void deleteCategory(String categoryId) throws IOException {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category Not Found By Id Exception"));
    String imagePath = imageUploadPath + category.getCoverImage();

    Path path = Paths.get(imagePath);
    Files.delete(path);
    categoryRepository.delete(category);
  }

  @Override
  public CategoryDto getCategoryById(String categoryId) {

    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category Not Found By Id Exception"));

    return modelMapper.map(category, CategoryDto.class);
  }

  @Override
  public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {

    Sort sort = ((sortDir.equalsIgnoreCase("desc"))) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy));
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    Page<Category> page = categoryRepository.findAll(pageable);
    PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
    return pageableResponse;
  }

  @Override
  public List<CategoryDto> searchCategory(String keywords) {

    List<Category> list = categoryRepository.findByTitleContaining(keywords);

    return list.stream().map((e) -> modelMapper.map(e, CategoryDto.class)).collect(Collectors.toList());

  }

}
