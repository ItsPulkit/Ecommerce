package com.project.ecommerce.services;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.project.ecommerce.dtos.UserDto;
import com.project.ecommerce.entities.Role;
import com.project.ecommerce.entities.User;
import com.project.ecommerce.repositories.RoleRepository;
import com.project.ecommerce.repositories.UserRepository;

@SpringBootTest
public class UserServiceTest {

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private RoleRepository roleRepository;
  @Autowired
  private UserService userService;

  User user;
  Role role;

  String roleId;

  @Autowired
  private ModelMapper mapper;

  @BeforeEach
  public void init() {
    role = Role.builder().roleId("abc").roleName("NORMAL").build();

    user = User.builder()
        .name("Abc")
        .email("abc@gmail.com")
        .about("testing")
        .gender("Male")
        .imageName("abc.png")
        .password("abc")
        .roles(Set.of(role))
        .build();

    roleId = "abc";
  }

  @Test
  public void createUserTest() {

    Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(role));
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

    UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
    System.out.println(user1.getName());
    Assertions.assertNotNull(user1, "User not saved");
    Assertions.assertEquals("Abc", user1.getName());

  }

}
