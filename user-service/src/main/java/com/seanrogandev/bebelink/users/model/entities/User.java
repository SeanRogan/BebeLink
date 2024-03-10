package com.seanrogandev.bebelink.users.model.entities;

import com.seanrogandev.bebelink.users.model.Role;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Accessors(chain = true)
@Getter
@Setter
@Table("users")

public class User implements UserDetails, Persistable<UUID> {
    //public class User {

    @Id
    @Column("id")
    private UUID userId;

    @Column("username")
    private String username;

    @Column("email")
    @Email
    private String email;

    @Column("password")
    private String password;

    @Column("create_date")
    @CreatedDate
    private LocalDateTime create_date;

    @Column("last_update")
    @LastModifiedDate
    private LocalDateTime last_update;

    @Column("profile_id")
    private UUID profileID;
    @Column("role")
    private Role role;
    @Transient
    private UserProfile profile;

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;

    }


    @Override
    public UUID getId() {
        return this.userId;
    }
    // userId must be null on object creation,
    // so the database will handle id generation
    @Override
    public boolean isNew() {
        return userId == null;
    }
}
