package com.generic.khatabook.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class GatewayConfig {


    public static final String DISCOVERY_SERVICE = "/discovery-service/**";

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        final RouteLocatorBuilder.Builder route = builder.routes()
//                .route("discovery-service", r -> r.path(DISCOVERY_SERVICE)
//                        .filters(x -> x.changeRequestUri()).uri(
//                                "http" +
//                                "://localhost:8761/"))
                .route("discovery-service-resource", r -> r.path(DISCOVERY_SERVICE).uri("http://localhost:8761/"));

        return route.build();
    }
}
