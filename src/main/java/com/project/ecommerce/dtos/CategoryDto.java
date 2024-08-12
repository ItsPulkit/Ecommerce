package com.project.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

  private String categoryId;

  @NotBlank
  @Size(min = 4, message = "Title Must Be Greater Than 4")
  private String title;

  @NotBlank(message = "Description Required")
  private String description;

  @NotBlank(message = "Image Required")
  private String coverImage;

}
