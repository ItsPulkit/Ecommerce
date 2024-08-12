package com.project.ecommerce.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration

@SecurityScheme(name = "scheme1", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@OpenAPIDefinition(

    info = @Info(title = "Emphoria APIs", description = "Emphoria E - Commerce Backend", version = "1.0V", contact = @Contact(name = "Emphoria", email = "", url = ""), license = @License(name = "OPEN License", url = "")), externalDocs = @ExternalDocumentation(description = "This is external docs", url = ""))
public class SwaggerConfig {

  // @Bean
  // public Docket docket() {
  // Docket docket = new Docket(DocumentationType.SWAGGER_2);
  // docket.apiInfo(getApiInfo());

  // return docket;

  // }

  // private ApiInfo getApiInfo() {

  // ApiInfo apiInfo = new ApiInfo(
  // "Emphoria E - Commerce Backend ",
  // "Emphoria APIs ",
  // "1.0.0V",
  // "",
  // new Contact("Emphoria", "", ""),
  // "License of APIS",
  // "About Emphoria",
  // new ArrayDeque<>());

  // return apiInfo;

  // }

}
