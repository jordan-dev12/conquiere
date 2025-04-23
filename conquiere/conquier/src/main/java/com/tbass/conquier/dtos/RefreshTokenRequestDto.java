package com.tbass.conquier.dtos;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDto(@NotBlank String refreshToken, @NotBlank String username) {

}
