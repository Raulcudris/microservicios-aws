package com.makiia.gateway.config;

import com.makiia.gateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("msvc-user", r -> r.path("/v1/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://msvc-user"))

                .route("msvc-jobservice", r -> r.path("/v1/job-service/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://msvc-jobservice"))

                .route("msvc-notification", r -> r.path("/v1/notification/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://msvc-notification"))

                .route("msvc-auth", r -> r.path("/v1/auth/**")
                        .uri("lb://msvc-auth"))

                .route("msvc-filestorage", r -> r.path("/v1/file-storage/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://msvc-filestorage"))
                .build();
    }
}
