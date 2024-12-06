package com.grabbingthecode.gatewayservice.gatewayfilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class GatewayGlobalFilter implements GlobalFilter {

    private final WebClient webClient;
    private static final List<String> SKIP_URIS = List.of("/api/v1/auth/authenticate");

    public GatewayGlobalFilter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8030").build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String pathsToSkip = exchange.getRequest().getURI().getPath();
        log.info("Path to skip: {}", pathsToSkip);
        if (skipPath(pathsToSkip)) {
            log.info("Skipping auth paths: {}", pathsToSkip);
            return chain.filter(exchange);
        }

        if (exchange.getRequest().getURI().getHost().equals("localhost") &&
                exchange.getRequest().getURI().getPort() == 8761) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("Authorization header missing or invalid");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        log.info("Token: {}", token);

        return webClient.post()
                .uri("/api/v1/gateway-request/validate")
                .bodyValue(token)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Invalid Token")))
                .toBodilessEntity()
                .flatMap(response -> {
                    log.info("Token validation successful");
                    return chain.filter(exchange);
                })
                .onErrorResume(e -> {
                    log.info("Token validation failed, Kindly Authenticate again :-)");
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    private boolean skipPath(String path) {
        return SKIP_URIS.stream().anyMatch(uri -> path.equalsIgnoreCase(uri)
        || path.startsWith(uri));
    }
}
