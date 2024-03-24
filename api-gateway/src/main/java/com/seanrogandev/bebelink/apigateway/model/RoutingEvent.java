package com.seanrogandev.bebelink.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class RoutingEvent {
    private String requestPath;
    private String localAddress;
    private String remoteAddress;
}
