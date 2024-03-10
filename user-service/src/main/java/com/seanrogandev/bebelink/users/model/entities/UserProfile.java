package com.seanrogandev.bebelink.users.model.entities;

import com.seanrogandev.bebelink.users.model.dto.UserDTO;
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

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Accessors(chain = true)
@Getter
@Setter
@Table("user_profile")
public class UserProfile implements Persistable<UUID> {

    public UserProfile() {}
    public UserProfile(User user){
        this.userId = user.getUserId();
    }
    @Id
    @Column("id")
    private UUID profileId;

    @Column("user_id")
    private UUID userId;

    @Column("create_date")
    @CreatedDate
    private LocalDateTime create_date;
    @Column("last_update")
    @LastModifiedDate
    private LocalDateTime last_update;
    @Transient
    private Set<ShortUrl> associated_Urls;

    @Override
    public UUID getId() {
        return this.profileId;
    }
    // userId must be null on object creation,
    // so the database will handle id generation
    @Override
    public boolean isNew() {
        return profileId == null;
    }

    public void updateFromDto(UserDTO user) {
        this.associated_Urls = user.getLinks();
    }
}

