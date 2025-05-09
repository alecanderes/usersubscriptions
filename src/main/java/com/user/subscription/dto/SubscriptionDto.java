package com.user.subscription.dto;

import jakarta.validation.constraints.NotBlank;

public record SubscriptionDto(
    Long id,
    @NotBlank(message = "Название подписки не должно быть пустым") String name
) {
}