package com.project.ecommerce.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.ecommerce.dtos.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponseMessage> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
    logger.info("Resource Not Found Exception Handler Invoked");
    ApiResponseMessage message = new ApiResponseMessage().builder()
        .message(ex.getMessage())
        .status(HttpStatus.NOT_FOUND)
        .success(true).build();
    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> MethodArgumentNotValidExceptionHandler(
      MethodArgumentNotValidException ex) {

    List<ObjectError> errors = ex.getBindingResult().getAllErrors();
    Map<String, Object> response = new HashMap<>();
    errors.stream().forEach(error -> {
      String message = error.getDefaultMessage();
      String field = ((FieldError) error).getField();
      response.put(field, message);
    });
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PropertyReferenceException.class)
  public ResponseEntity<ApiResponseMessage> PropertyReferenceExceptionHandler(
      PropertyReferenceException ex) {
    ApiResponseMessage response = new ApiResponseMessage().builder().message(ex.getMessage())
        .status(HttpStatus.BAD_REQUEST).success(true).build();
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadApiRequestionException.class)
  public ResponseEntity<ApiResponseMessage> BadApiRequestionExceptionHandler(BadApiRequestionException ex) {
    logger.info("Bad API Request Exception Handler Invoked");
    ApiResponseMessage message = new ApiResponseMessage().builder()
        .message(ex.getMessage())
        .status(HttpStatus.BAD_REQUEST)
        .success(false).build();
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

  }
}
