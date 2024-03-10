package com.seanrogandev.bebelink.users.model.dto;

import com.seanrogandev.bebelink.users.model.Role;
import com.seanrogandev.bebelink.users.model.entities.ShortUrl;
import com.seanrogandev.bebelink.users.model.entities.User;
import com.seanrogandev.bebelink.users.model.entities.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

/**
 * UserDTO is a data transfer object which combines info from
 * the User and UserProfile Entities into one object.
 */


@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class UserDTO {

    private UUID userId;

    private String username;

    private String email;

    private String password;

    private Role role;

    private UUID profileId;

    private Set<ShortUrl> links;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.profileId = user.getProfile().getProfileId();
        this.links = user.getProfile().getAssociated_Urls();
    }

    public UserDTO(User user, UserProfile profile) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.profileId = profile.getProfileId();
        this.links = profile.getAssociated_Urls();
    }
}
