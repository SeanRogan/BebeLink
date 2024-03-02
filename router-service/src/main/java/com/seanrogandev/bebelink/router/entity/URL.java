package com.seanrogandev.bebelink.router.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter

public class URL {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private String uri;
    @OneToMany(fetch= FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="url")
    @JsonManagedReference("url-events")
    private Set<RoutingEvent> events;

    private int totalRedirects;
    public URL() {}

}
