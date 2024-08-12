package com.project.ecommerce.exceptions;

public class BadApiRequestionException extends RuntimeException {
  public BadApiRequestionException() {
    super("Bad API Request Exception");

  }

  public BadApiRequestionException(String message) {
    super(message);
  }
}
