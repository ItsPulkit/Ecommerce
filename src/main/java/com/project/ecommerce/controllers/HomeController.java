package com.project.ecommerce.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {
  @GetMapping
  public String testing() {
    return "Welcome to the Ecommerce Application";
  }

}
