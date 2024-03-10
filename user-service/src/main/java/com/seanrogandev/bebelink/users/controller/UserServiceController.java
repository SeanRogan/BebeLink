package com.seanrogandev.bebelink.users.controller;

import com.seanrogandev.bebelink.users.model.dto.RegisterRequestDTO;
import com.seanrogandev.bebelink.users.model.dto.UserDTO;
import com.seanrogandev.bebelink.users.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class UserServiceController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public Mono<ResponseEntity<UserDTO>> getUser(@PathVariable("id") @Valid UUID id) {
        return userService.getUserById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/user")
    public Mono<ResponseEntity<UserDTO>> createUser(@RequestBody @Valid Mono<RegisterRequestDTO> registerRequest) {
        return userService.registerUser(registerRequest)
                .map(dto -> ResponseEntity.ok(dto))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/user/{id}")
    public Mono<ResponseEntity<UserDTO>> updateUser(@PathVariable("id") @Valid UUID id, @RequestBody @Valid Mono<UserDTO> userDtoMono) {
        return userService.updateUser(id, userDtoMono)
                .map(dto -> ResponseEntity.ok(dto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/user/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") @Valid UUID id) {
        return userService.deleteUser(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
