package com.project.ecommerce.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.project.ecommerce.dtos.PageableResponse;

public class Helper {

  public static <V, U> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type)

  {
    List<U> entity = page.getContent();
    List<V> dtoList = entity.stream().map((object) -> new ModelMapper().map(object, type)).collect(Collectors.toList());

    PageableResponse<V> response = new PageableResponse<>();
    response.setContent(dtoList);
    response.setPageNumber(page.getNumber());
    response.setLastPage(page.isLast());
    response.setPageSize(page.getSize());
    response.setTotalElements(page.getNumberOfElements());
    response.setTotalPages(page.getTotalPages());
    return response;
  }

}
