package com.project.ecommerce.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.ecommerce.dtos.ApiResponseMessage;
import com.project.ecommerce.dtos.CategoryDto;
import com.project.ecommerce.dtos.ImageResponse;
import com.project.ecommerce.dtos.PageableResponse;
import com.project.ecommerce.dtos.ProductDto;
import com.project.ecommerce.services.CategoryService;
import com.project.ecommerce.services.FileService;
import com.project.ecommerce.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  @Autowired
  CategoryService categoryService;
  @Autowired
  private ProductService productService;

  @Value("${category.profile.image.path}")
  private String imageUploadPath;
  @Autowired
  FileService fileService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
    return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
      @PathVariable("categoryId") String categoryId) {

    return new ResponseEntity<>(categoryService.updateCategoryDto(categoryDto, categoryId), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{categoryId}")
  public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("categoryId") String categoryId)
      throws IOException {
    categoryService.deleteCategory(categoryId);
    ApiResponseMessage response = ApiResponseMessage.builder()
        .message("Category Deleted Succesfully")
        .status(HttpStatus.OK).success(true).build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
    return new ResponseEntity<>(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") String categoryId) {
    return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
  }

  @GetMapping("/search/{keywords}")
  public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable("keywords") String keywords) {
    return new ResponseEntity<>(categoryService.searchCategory(keywords), HttpStatus.OK);
  }

  @PostMapping("/image/{categoryId}")
  public ResponseEntity<ImageResponse> uploadimage(@PathVariable("categoryId") String categoryId,
      @RequestParam("categoryImage") MultipartFile categoryImage) throws IOException {

    String imageName = fileService.uploadFile(categoryImage, imageUploadPath);

    CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
    categoryDto.setCoverImage(imageName);
    categoryService.updateCategoryDto(categoryDto, categoryId);
    ImageResponse response = ImageResponse.builder().imageName(imageName).imagePath(imageUploadPath + imageName)
        .message("File Uploaded")
        .status(HttpStatus.CREATED).success(true).build();
    return new ResponseEntity<>(response, HttpStatus.CREATED);

  }

  @GetMapping("/image/{categoryId}")
  public void getCategoryImage(@PathVariable("categoryId") String categoryId, HttpServletResponse response)
      throws IOException {
    CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
    String imageName = categoryDto.getCoverImage();
    InputStream resource = fileService.getResource(imageUploadPath, imageName);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resource, response.getOutputStream());
  }

  @PostMapping("/{categoryId}/products")
  public ResponseEntity<ProductDto> createProductWithCategory(
      @PathVariable("categoryId") String categoryId,
      @RequestBody ProductDto dto) {
    ProductDto productWithCategory = productService.createWithCategory(dto, categoryId);
    return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
  }

  // update category of product
  @PutMapping("/{categoryId}/products/{productId}")
  public ResponseEntity<ProductDto> updateCategoryOfProduct(
      @PathVariable String categoryId,
      @PathVariable String productId) {
    ProductDto productDto = productService.updateCategory(productId, categoryId);
    return new ResponseEntity<>(productDto, HttpStatus.OK);
  }

  // get products of categories
  @GetMapping("/{categoryId}/products")
  public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
      @PathVariable String categoryId,
      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

    PageableResponse<ProductDto> response = productService.getAllOfCategory(categoryId, pageNumber, pageSize, sortBy,
        sortDir);
    return new ResponseEntity<>(response, HttpStatus.OK);

  }

}
