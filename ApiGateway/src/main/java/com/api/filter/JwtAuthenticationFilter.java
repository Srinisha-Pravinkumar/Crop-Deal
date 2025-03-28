package com.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import com.api.util.Jwtutil;
import org.springframework.http.HttpMethod;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private Jwtutil jwtUtil;

    @Autowired
    private RouteValidator validator;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.error("Missing Authorization header");
                    throw new RuntimeException("Missing Authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    jwtUtil.validateToken(authHeader);
                    String role = jwtUtil.extractRole(authHeader);
                    String path = exchange.getRequest().getURI().getPath();
                    HttpMethod method = exchange.getRequest().getMethod();

                    if (!checkRoleAccess(role, path, method)) {
                        logger.error("Unauthorized access: Role '{}' does not have access to path '{}'", role, path);
                        throw new RuntimeException("Unauthorized access");
                    }
                } catch (Exception e) {
                    logger.error("Invalid access: {}", e.getMessage());
                    throw new RuntimeException("Unauthorized access to the application");
                }
            }
            return chain.filter(exchange);
        };
    }


    private boolean checkRoleAccess(String role, String path, HttpMethod method) {
        switch (role) {
            case "ADMIN":
                return path.startsWith("/admin") || path.startsWith("/farmer")  || path.startsWith("/dealer");
            case "FARMER":
                return path.startsWith("/farmer") || (path.startsWith("/dealer") && method.equals(HttpMethod.GET)) || path.startsWith("/crop");
            case "DEALER":
                return path.startsWith("/dealer") || path.startsWith("/crop");
            default:
                return false;
        }
    }


    public static class Config {
    }
}
