package com.user.subscription.service;

import com.user.subscription.domain.Subscription;
import com.user.subscription.dto.SubscriptionDto;
import com.user.subscription.exception.SubscriptionNotFoundException;
import com.user.subscription.mapstruct.SubscriptionMapper;
import com.user.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionDto createSubscription(SubscriptionDto dto) {
        Subscription subscription = subscriptionMapper.toEntity(dto);
        return subscriptionMapper.toDto(subscriptionRepository.save(subscription));
    }

    public SubscriptionDto getSubscription(Long subId) {
        Subscription subscription = subscriptionRepository.findById(subId)
            .orElseThrow(() -> new SubscriptionNotFoundException(subId));
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
        return toDto(subscriptionRepository.findTopThreeSubscriptions());
    }

    private Set<SubscriptionDto> toDto(Collection<Subscription> subscriptions) {
        return subscriptions.stream()
            .map(subscriptionMapper::toDto)
            .collect(Collectors.toSet());
    }
}