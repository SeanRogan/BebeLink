package com.seanrogandev.bebelink.users.repository;

import com.seanrogandev.bebelink.users.model.entities.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Repository
public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<User> findById(UUID id);
    Mono<User> findByEmail(String email);

}
