package com.project.ecommerce.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.ecommerce.dtos.PageableResponse;
import com.project.ecommerce.dtos.UserDto;
import com.project.ecommerce.entities.Role;
import com.project.ecommerce.entities.User;
import com.project.ecommerce.exceptions.ResourceNotFoundException;
import com.project.ecommerce.repositories.RoleRepository;
import com.project.ecommerce.repositories.UserRepository;
import com.project.ecommerce.services.UserService;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Value("${user.profile.image.path}")
  private String imageUploadPath;
  @Value("${normal.role.id}")
  private String normalRoleId;
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public UserDto createUser(UserDto userDto) {
    String s = UUID.randomUUID().toString();
    userDto.setUserId(s);
    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
    User user = dtoToEntity(userDto);
    Role role = roleRepository.findById(normalRoleId).get();
    user.getRoles().add(role);
    User savedUser = userRepository.save(user);

    UserDto newUserDto = entityToDto(savedUser);
    return newUserDto;

  }

  @Override
  public UserDto updateUser(UserDto userDto, String userID) {

    User user = userRepository.findById(userID)
        .orElseThrow(() -> new ResourceNotFoundException("User Not Found By User Id Exception"));
    user.setAbout(userDto.getAbout());
    user.setGender(userDto.getGender());
    user.setEmail(userDto.getEmail());
    if (!userDto.getPassword().equalsIgnoreCase(user.getPassword()))
      user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setImageName(userDto.getImageName());
    user.setName(userDto.getName());
    user.setPassword(userDto.getPassword());

    User updatedUser = userRepository.save(user);

    UserDto newUserDto = entityToDto(updatedUser);
    return newUserDto;

  }

  @Override
  public void deleteUser(String userId) throws IOException {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User Not Found By User Id Exception"));
    String imagePath = imageUploadPath + user.getImageName();

    Path path = Paths.get(imagePath);
    Files.delete(path);
    userRepository.delete(user);
    return;

  }

  @Override
  public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

    Sort sort = ((sortDir.equalsIgnoreCase("desc"))) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy));
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<User> users = userRepository.findAll(pageable);
    List<User> list = users.getContent();
    List<UserDto> dtoList = list.stream().map((user) -> entityToDto(user)).collect(Collectors.toList());

    PageableResponse<UserDto> response = new PageableResponse<>();
    response.setContent(dtoList);
    response.setPageNumber(users.getNumber());
    response.setLastPage(users.isLast());
    response.setPageSize(users.getSize());
    response.setTotalElements(users.getNumberOfElements());
    response.setTotalPages(users.getTotalPages());
    return response;

  }

  @Override
  public UserDto getUserById(String UserId) {
    User user = userRepository.findById(UserId)
        .orElseThrow(() -> new ResourceNotFoundException("User Not Found By User Id  Exception"));
    return entityToDto(user);
  }

  @Override
  public UserDto getUserByEmail(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User Not Found By Email Exception"));
    return entityToDto(user);
  }

  @Override
  public List<UserDto> searchUser(String keyword) {
    List<User> usersList = userRepository.findByNameContaining(keyword);
    List<UserDto> dtoList = usersList.stream().map((user) -> entityToDto(user)).collect(Collectors.toList());
    return dtoList;
  }

  private UserDto entityToDto(User savedUser) {
    // UserDto userDto = UserDto.builder()
    // .userId(savedUser.getUserId())
    // .name(savedUser.getName())
    // .email(savedUser.getEmail())
    // .password(savedUser.getPassword())
    // .about(savedUser.getAbout())
    // .gender(savedUser.getGender())
    // .imageName(savedUser.getImageName())
    // .build();

    return modelMapper.map(savedUser, UserDto.class);
  }

  private User dtoToEntity(UserDto userDto) {
    // User user = User.builder()
    // .userId(userDto.getUserId())
    // .name(userDto.getName())
    // .email(userDto.getEmail())
    // .password(userDto.getPassword())
    // .about(userDto.getAbout())
    // .gender(userDto.getGender())
    // .imageName(userDto.getImageName())
    // .build();
    return modelMapper.map(userDto, User.class);
  }

}
