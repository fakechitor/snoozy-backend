package org.snoozy.snoozygateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.snoozy.snoozygateway.service.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthenticationFilterGateway
        extends AbstractGatewayFilterFactory<JwtAuthenticationFilterGateway.Config> {

    private final JwtService jwtService;

    public JwtAuthenticationFilterGateway(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            try {
                Claims claims = jwtService.parseToken(token);

                String subject = claims.getSubject();
                String email = claims.get("email", String.class);
                String authType = claims.get("auth", String.class);

                ServerHttpRequest mutatedRequest = exchange.getRequest()
                        .mutate()
                        .header("X-Auth-Type", authType != null ? authType : "")
                        .header("X-User-Id", subject != null ? subject : "")
                        .header("X-User-Email", email != null ? email : "")
                        .build();

                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            } catch (JwtException | IllegalArgumentException e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }
}