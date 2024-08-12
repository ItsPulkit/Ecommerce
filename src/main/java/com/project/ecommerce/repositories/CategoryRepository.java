package com.project.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
  List<Category> findByTitleContaining(String keywords);
}
