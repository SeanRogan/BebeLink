package com.seanrogandev.bebelink.generator.model.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
@Accessors(chain = true)
@Getter
@Setter
@Table("url")
public class ShortUrl implements Persistable<UUID> {

    @Id
    @Column("id")
    private UUID id;
    @NotEmpty
    @Column("origin")
    private String origin;
    @NotEmpty
    @Column("short_url")
    private String shortUrl;
    @Column("create_date")
    @CreatedDate
    private LocalDateTime createDate;
    @Column("last_updated")
    @LastModifiedDate
    private LocalDateTime lastUpdated;
    @Column("expires")
    private LocalDateTime expires;
    @Column("active")
    private boolean active;

    public ShortUrl() {
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
