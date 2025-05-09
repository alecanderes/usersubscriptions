package com.user.subscription.mapstruct;

import com.user.subscription.domain.Subscription;
import com.user.subscription.dto.SubscriptionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionDto toDto(Subscription user);

    Subscription toEntity(SubscriptionDto userDto);
}