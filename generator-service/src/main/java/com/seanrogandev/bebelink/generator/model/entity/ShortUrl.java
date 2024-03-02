package com.seanrogandev.bebelink.generator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.UUID;
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter

public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;
    @NotEmpty
    @Column(name = "origin_url", updatable = false, nullable = false, unique = true)
    private String origin;
    @NotEmpty
    @Column(name = "shortened_url", updatable = false, nullable = false, unique = true)
    private String url;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    @CreatedDate
    private Date createdOn;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    @LastModifiedDate
    private Date lastUpdate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;
    private boolean active;

    public ShortUrl() {

    }
}
