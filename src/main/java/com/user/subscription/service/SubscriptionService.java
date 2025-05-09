package com.user.subscription.service;

import com.user.subscription.domain.Subscription;
import com.user.subscription.dto.SubscriptionDto;
import com.user.subscription.exception.DuplicateSubscriptionException;
import com.user.subscription.exception.SubscriptionNotFoundException;
import com.user.subscription.mapstruct.SubscriptionMapper;
import com.user.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionDto createSubscription(SubscriptionDto dto) {
        log.info("Создаём подписку: name = {}", dto.name());
        Subscription subscription = subscriptionMapper.toEntity(dto);
        Subscription savedSubscription;
        try {
            savedSubscription = subscriptionRepository.save(subscription);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateSubscriptionException(subscription.getName());
        }
        log.info("Подписка создана: id = {}, name = {}", savedSubscription.getId(), savedSubscription.getName());
        return subscriptionMapper.toDto(savedSubscription);
    }

    public SubscriptionDto getSubscription(Long subId) {
        Subscription subscription = subscriptionRepository.findById(subId)
            .orElseThrow(() -> {
                log.warn("Подписка не найдена с id =  {}", subId);
                return new SubscriptionNotFoundException(subId);
            });
        return subscriptionMapper.toDto(subscription);
    }

    //FIXME Pageable
    public Set<SubscriptionDto> getAllSubscriptions() {
        return toDto(subscriptionRepository.findAll());
    }

    public void deleteSubscription(Long subId) {
        subscriptionRepository.deleteById(subId);
    }

    public Set<SubscriptionDto> getTopSubscriptions() {
        log.info("Получаем топ 3 подписки");
        Set<Subscription> topThreeSubscriptions = subscriptionRepository.findTopThreeSubscriptions();
        log.info("Получены 3 подписки");
        return toDto(topThreeSubscriptions);
    }

    private Set<SubscriptionDto> toDto(Collection<Subscription> subscriptions) {
        return subscriptions.stream()
            .map(subscriptionMapper::toDto)
            .collect(Collectors.toSet());
    }
}