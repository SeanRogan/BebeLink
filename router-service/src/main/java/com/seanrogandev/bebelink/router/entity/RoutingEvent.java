package com.seanrogandev.bebelink.router.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.UUID;
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter

public class RoutingEvent {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private String originUrl;
    private String shortUrl;
    @CreatedDate
    private Date createdOn;
    @LastModifiedDate
    private Date lastUpdate;

    @ManyToOne()
    @JsonBackReference("url-events")
    @JoinColumn(name = "url")
    private URL url;

    public RoutingEvent() {}
}
