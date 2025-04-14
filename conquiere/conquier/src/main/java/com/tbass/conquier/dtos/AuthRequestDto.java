package com.tbass.conquier.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDto(@NotBlank String username, @NotBlank String password) {

}
