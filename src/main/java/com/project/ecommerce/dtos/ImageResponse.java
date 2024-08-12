package com.project.ecommerce.dtos;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImageResponse {
  public String imageName;
  public String imagePath;
  public String message;
  public Boolean success;
  public HttpStatus status;

}
