package com.user.subscription.exception;

public class DuplicateSubscriptionException extends RuntimeException {

    public DuplicateSubscriptionException(String name) {
        super("Подписка с именем " + name + " уже существует");
    }
}