package com.project.ecommerce.dtos;

import java.util.HashSet;
import java.util.Set;

import com.project.ecommerce.validate.ImageNameValidate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UserDto {

  private String userId;
  @Size(min = 3, max = 25, message = "Enter  Name Range 3-25")
  private String name;
  // @Email(message = "Enter Valid Email")
  @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Enter Valid Email")
  @NotBlank(message = "Enter Email")
  private String email;
  @NotBlank(message = "Enter Password")
  private String password;

  @Size(min = 4, max = 6, message = "Choose Male or Female")
  private String gender;
  @NotBlank(message = "Fill About")
  private String about;
  @ImageNameValidate
  private String imageName;

  private Set<RoleDto> roles = new HashSet<>();
}
