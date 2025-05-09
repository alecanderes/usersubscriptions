package com.user.subscription.repository;

import com.user.subscription.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = """
        SELECT s.* FROM subscriptions s
        INNER JOIN user_subscriptions su ON s.id = su.subscription_id
        GROUP BY s.id
        ORDER BY COUNT(su.user_id) DESC
        LIMIT 3
        """, nativeQuery = true)
    Set<Subscription> findTopThreeSubscriptions();
}