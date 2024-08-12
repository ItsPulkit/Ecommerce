package com.project.ecommerce.exceptions;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException() {
    super("Resouce Not Found Exception");
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }

}
