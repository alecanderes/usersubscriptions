package com.user.subscription.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDto(
    Long id,
    @NotBlank(message = "Имя пользователя не должно быть пустым") String name
) {
}