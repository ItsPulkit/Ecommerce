package com.project.ecommerce.services;

import com.project.ecommerce.dtos.RefreshTokenDto;
import com.project.ecommerce.dtos.UserDto;

public interface RefreshTokenService {

    // create
    RefreshTokenDto createRefreshToken(String username);

    // find by token
    RefreshTokenDto findByToken(String token);
    // verify

    RefreshTokenDto verifyRefreshToken(RefreshTokenDto refreshTokenDto);

    UserDto getUser(RefreshTokenDto dto);

}
