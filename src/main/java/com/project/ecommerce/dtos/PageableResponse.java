package com.project.ecommerce.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T> {
  private List<T> content;
  private int pageNumber;
  private int pageSize;
  private int totalPages;
  private long totalElements;
  private boolean lastPage;

}
