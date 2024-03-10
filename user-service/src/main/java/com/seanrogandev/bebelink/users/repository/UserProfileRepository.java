package com.seanrogandev.bebelink.users.repository;

import com.seanrogandev.bebelink.users.model.entities.UserProfile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Repository
public interface UserProfileRepository extends ReactiveCrudRepository<UserProfile, UUID> {
    Mono<UserProfile> findById(UUID id);

    Mono<UserProfile> findByUserId(UUID id);
}
