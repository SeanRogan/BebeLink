package com.seanrogandev.bebelink.users.model.dto;

import com.seanrogandev.bebelink.users.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequestDTO {
    private String email;
    private String username;
    private String password;
    private Role role;
}
