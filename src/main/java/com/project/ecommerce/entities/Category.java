package com.project.ecommerce.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

  @Id
  @Column(name = "id")
  private String categoryId;

  @Column(name = "category_title", length = 60, nullable = false)
  private String title;

  @Column(name = "category_desc", length = 500)
  private String description;

  private String coverImage;
  // other attributes if you have...

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Product> products = new ArrayList<>();

}