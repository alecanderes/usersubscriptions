package com.user.subscription.rest;

import com.user.subscription.dto.SubscriptionDto;
import com.user.subscription.dto.UserDto;
import com.user.subscription.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/subscriptions")
    public ResponseEntity<Void> addSubscription(
        @PathVariable Long id,
        @RequestParam Long subscriptionId
    ) {
        userService.addSubscription(id, subscriptionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<Set<SubscriptionDto>> getSubscriptions(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserSubscriptions(id));
    }

    @DeleteMapping("/{id}/subscriptions/{subId}")
    public ResponseEntity<Void> removeSubscription(@PathVariable Long id, @PathVariable Long subId) {
        userService.removeSubscription(id, subId);
        return ResponseEntity.noContent().build();
    }
}