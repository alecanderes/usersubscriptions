package com.user.subscription.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long notFoundUserId) {
        super("Пользователь с id " + notFoundUserId + " не найден");
    }
}