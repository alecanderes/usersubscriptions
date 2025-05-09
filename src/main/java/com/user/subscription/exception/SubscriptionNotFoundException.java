package com.user.subscription.exception;

public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException(Long notFoundSubscriptionId) {
        super("Подписка с id " + notFoundSubscriptionId + " не найдена");
    }
}