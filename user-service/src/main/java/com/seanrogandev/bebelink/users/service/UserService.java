package com.seanrogandev.bebelink.users.service;

import com.seanrogandev.bebelink.users.model.dto.RegisterRequestDTO;
import com.seanrogandev.bebelink.users.model.dto.UserDTO;
import com.seanrogandev.bebelink.users.model.entities.User;
import com.seanrogandev.bebelink.users.model.entities.UserProfile;
import com.seanrogandev.bebelink.users.repository.UserProfileRepository;
import com.seanrogandev.bebelink.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public Mono<UserDTO> getUserById(UUID id) {
        Mono<User> userMono = userRepository.findById(id);
        Mono<UserProfile> profileMono = userProfileRepository.findByUserId(id);
        return Mono.zip(userMono, profileMono, UserDTO::new);
    }
    public Mono<UserDTO> getUserByEmail(String email) {
        Mono<User> userMono = userRepository.findByEmail(email);
        Mono<UserProfile> profileMono = userMono.flatMap(user -> userProfileRepository.findById(user.getProfileID()));
        return Mono.zip(userMono, profileMono, (user, profile) -> {
            user.setProfile(profile);
            return new UserDTO(user, profile);
        });
    }
    public Mono<UserDTO> registerUser(Mono<RegisterRequestDTO> registerRequest) {
        return registerRequest.flatMap(req -> {
            User userEntity = new User()
                    .setUserId(null)
                    .setUsername(req.getUsername())
                    .setEmail(req.getEmail())
                    //todo password encoding
                    .setPassword(req.getPassword())
                    .setRole(req.getRole());
            return userRepository.save(userEntity)
                    .flatMap(savedUser -> {
                        UserProfile profile = new UserProfile(savedUser);
                        return userProfileRepository.save(profile)
                                .map(savedProfile -> {
                                    savedUser.setProfile(savedProfile);
                                    return new UserDTO(savedUser, savedProfile);
                                });
                    });
        });
    }
    public Mono<UserDTO> updateUser(UUID userId, Mono<UserDTO> userDtoMono) {
        return userDtoMono.flatMap(dto ->
                userRepository.findById(userId)
                        .flatMap(existingUser -> {
                            // Update user properties
                            existingUser.setUsername(dto.getUsername())
                                    .setEmail(dto.getEmail())
                                    .setPassword(dto.getPassword()) // Assume password encoding is handled elsewhere
                                    .setRole(dto.getRole());

                            // Update the UserProfile, assuming the profile exists
                            Mono<UserProfile> updatedProfileMono = userProfileRepository.findById(dto.getProfileId())
                                    .flatMap(existingProfile -> {
                                        // update some profile properties here
                                         existingProfile.updateFromDto(dto);
                                        return userProfileRepository.save(existingProfile);
                                    });

                            // Save the updated user and return a Mono.zip() combining both saved entities
                            return Mono.zip(userRepository.save(existingUser), updatedProfileMono, UserDTO::new);
                        })
        );
    }
    public Mono<Void> deleteUser(UUID id) {
        return userRepository.deleteById(id);
    }
}
