package com.project.ecommerce.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValidate, String> {
  Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    logger.info("Message From isValid Image Name Validator :{}", value);
    if (value.isBlank()) {
      return false;
    } else {
      return true;
    }
  }

}
