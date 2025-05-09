package com.user.subscription.service;

import com.user.subscription.domain.Subscription;
import com.user.subscription.domain.User;
import com.user.subscription.dto.SubscriptionDto;
import com.user.subscription.dto.UserDto;
import com.user.subscription.exception.UserNotFoundException;
import com.user.subscription.mapstruct.SubscriptionMapper;
import com.user.subscription.mapstruct.UserMapper;
import com.user.subscription.repository.SubscriptionRepository;
import com.user.subscription.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserMapper userMapper;
    private final SubscriptionMapper subscriptionMapper;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        log.info("Создаём пользователя: name = {}", userDto.name());
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        log.info("Пользователь создан: id = {}, name = {}", user.getId(), user.getName());
        return userMapper.toDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        return userMapper.toDto(findUserById(id));
    }

    @Transactional
    public UserDto updateUser(Long id, @Valid UserDto userDto) {
        User user = findUserById(id);
        user.setName(userDto.name());
        return userMapper.toDto(user);
    }

    @Transactional
    public void addSubscription(Long userId, Long subscriptionId) {
        User user = findUserById(userId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow();
        user.getSubscriptions().add(subscription);
    }

    public Set<SubscriptionDto> getUserSubscriptions(Long userId) {
        return findUserById(userId).getSubscriptions()
            .stream()
            .map(subscriptionMapper::toDto)
            .collect(Collectors.toSet());
    }

    @Transactional
    public void removeSubscription(Long userId, Long subscriptionId) {
        User user = findUserById(userId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow();
        user.getSubscriptions().remove(subscription);
    }

    private User findUserById(Long id) {
        log.info("Находим пользователя с id = {}", id);
        User foundUser = userRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Пользователь не найден с id =  {}", id);
                return new UserNotFoundException(id);
            });
        log.info("Пользователь с id = {} найден", id);
        return foundUser;
    }

    public void deleteUser(Long id) {
        log.info("Удаляем пользователя с id = {}", id);
        userRepository.deleteById(id);
        log.info("Пользователь с id = {} удален", id);
    }
}