package com.seanrogandev.bebelink.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RoutingEvent {
    private String requestPath;
    private String localAddress;
    private String remoteAddress;
}
