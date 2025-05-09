package com.user.subscription.rest;

import com.user.subscription.dto.SubscriptionDto;
import com.user.subscription.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionDto> create(@RequestBody @Valid SubscriptionDto dto) {
        return ResponseEntity.ok(subscriptionService.createSubscription(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getSubscription(id));
    }

    @GetMapping
    public ResponseEntity<Set<SubscriptionDto>> getAll() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top")
    public ResponseEntity<Set<SubscriptionDto>> getTopSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getTopSubscriptions());
    }
}