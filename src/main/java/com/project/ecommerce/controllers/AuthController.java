package com.project.ecommerce.controllers;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.dtos.JwtRequest;
import com.project.ecommerce.dtos.JwtResponse;
import com.project.ecommerce.dtos.RefreshTokenDto;
import com.project.ecommerce.dtos.RefreshTokenRequest;
import com.project.ecommerce.dtos.UserDto;
import com.project.ecommerce.entities.User;
import com.project.ecommerce.security.JwtHelper;
import com.project.ecommerce.services.RefreshTokenService;
import com.project.ecommerce.services.UserService;

@RestController
@RequestMapping("/auth")

public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtHelper jwtHelper;

  private Logger logger = LoggerFactory.getLogger(AuthController.class);

  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserService userService;

  @Autowired
  private RefreshTokenService refreshTokenService;

  @PostMapping("/regenerate-token")
  public ResponseEntity<JwtResponse> regenerateToken(@RequestBody RefreshTokenRequest request) {

    RefreshTokenDto refreshTokenDto = refreshTokenService.findByToken(request.getRefreshToken());
    RefreshTokenDto refreshTokenDto1 = refreshTokenService.verifyRefreshToken(refreshTokenDto);
    UserDto user = refreshTokenService.getUser(refreshTokenDto1);
    String jwtToken = jwtHelper.generateToken(modelMapper.map(user, User.class));

    JwtResponse response = JwtResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshTokenDto)
        .user(user)
        .build();
    return ResponseEntity.ok(response);

  }

  @PostMapping("/generate-token")
  public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

    logger.info("Username {} ,  Password {}", request.getEmail(), request.getPassword());

    this.doAuthenticate(request.getEmail(), request.getPassword());

    User user = (User) userDetailsService.loadUserByUsername(request.getEmail());

    String token = jwtHelper.generateToken(user);

    RefreshTokenDto refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

    JwtResponse jwtResponse = JwtResponse
        .builder()
        .token(token)
        .user(modelMapper.map(user, UserDto.class))
        .refreshToken(refreshToken)
        .build();

    return ResponseEntity.ok(jwtResponse);

  }

  private void doAuthenticate(String email, String password) {

    try {
      Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
      authenticationManager.authenticate(authentication);

    } catch (BadCredentialsException ex) {
      throw new BadCredentialsException("Invalid Username and Password !!");
    }

  }

  @RequestMapping("/currentuser")
  public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
    String user = principal.getName();

    return new ResponseEntity<>(modelMapper.map(userDetailsService.loadUserByUsername(user),
        UserDto.class),
        HttpStatus.OK);

  }

}
