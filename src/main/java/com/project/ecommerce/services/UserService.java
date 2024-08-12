package com.project.ecommerce.services;

import java.io.IOException;
import java.util.List;

import com.project.ecommerce.dtos.PageableResponse;
import com.project.ecommerce.dtos.UserDto;

public interface UserService {

  UserDto createUser(UserDto userDto);

  UserDto updateUser(UserDto userDto, String userID);

  void deleteUser(String userId) throws IOException;

  PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

  UserDto getUserById(String UserId);

  UserDto getUserByEmail(String email);

  List<UserDto> searchUser(String keyword);
}