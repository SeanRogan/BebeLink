package com.seanrogandev.bebelink.analyticsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RoutingEvent {
    private String requestPath;
    private String localAddress;
    private String remoteAddress;
}
