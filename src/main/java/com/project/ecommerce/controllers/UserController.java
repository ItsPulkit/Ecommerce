package com.project.ecommerce.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.ecommerce.dtos.ApiResponseMessage;
import com.project.ecommerce.dtos.ImageResponse;
import com.project.ecommerce.dtos.PageableResponse;
import com.project.ecommerce.dtos.UserDto;
import com.project.ecommerce.services.FileService;
import com.project.ecommerce.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private FileService fileService;

  @Value("${user.profile.image.path}")
  private String imageUploadPath;

  @PostMapping
  @Operation(summary = "create new user !!", description = "this is user api")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success | OK"),
      @ApiResponse(responseCode = "401", description = "not authorized !!"),
      @ApiResponse(responseCode = "201", description = "new user created !!")
  })
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
    UserDto userDto1 = userService.createUser(userDto);
    return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
      @PathVariable("userId") String userId) {
    UserDto updatedUserDto1 = userService.updateUser(userDto, userId);
    return new ResponseEntity<>(updatedUserDto1, HttpStatus.OK);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) throws IOException {
    userService.deleteUser(userId);
    ApiResponseMessage message = ApiResponseMessage.builder().message("User Was Deleted By User Id ")
        .success(true).status(HttpStatus.OK).build();
    return new ResponseEntity<>(message, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
    PageableResponse<UserDto> usersList = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<>(usersList, HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId) {
    UserDto userDto = userService.getUserById(userId);
    return new ResponseEntity<>(userDto, HttpStatus.OK);
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
    UserDto userDto = userService.getUserByEmail(email);
    return new ResponseEntity<>(userDto, HttpStatus.OK);
  }

  @GetMapping("/search/{keywords}")
  public ResponseEntity<List<UserDto>> getUserByKeywords(@PathVariable("keywords") String keywords) {
    List<UserDto> userDtoList = userService.searchUser(keywords);
    return new ResponseEntity<>(userDtoList, HttpStatus.OK);
  }

  @PostMapping("/image/{userId}")
  public ResponseEntity<ImageResponse> uploadimage(@PathVariable("userId") String userId,
      @RequestParam("userImage") MultipartFile userImage) throws IOException {

    String imageName = fileService.uploadFile(userImage, imageUploadPath);

    UserDto userDto = userService.getUserById(userId);
    userDto.setImageName(imageName);
    userService.updateUser(userDto, userId);
    ImageResponse response = ImageResponse.builder().imageName(imageName).imagePath(imageUploadPath + imageName)
        .message("File Uploaded")
        .status(HttpStatus.CREATED).success(true).build();
    return new ResponseEntity<>(response, HttpStatus.CREATED);

  }

  @GetMapping("/image/{userId}")
  public void getUserImage(@PathVariable("userId") String userId, HttpServletResponse response)
      throws IOException {
    UserDto userdto = userService.getUserById(userId);
    String imageName = userdto.getImageName();
    InputStream resource = fileService.getResource(imageUploadPath, imageName);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resource, response.getOutputStream());
  }
}
