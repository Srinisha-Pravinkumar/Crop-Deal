package com.api.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final Logger logger = LoggerFactory.getLogger(RouteValidator.class);

    public static final List<String> openApiEndpoints = List.of(
    	    "/profiles/register",
    	    "/profiles/login",
    	    "/auth/token",
    	    "/auth/validate"
    	);

    	public Predicate<ServerHttpRequest> isSecured = request -> {
    	    String path = request.getURI().getPath();
    	    boolean isSecured = openApiEndpoints.stream().noneMatch(uri -> uri.equals(path));
    	    logger.info("Path '{}' is public: {}", path, isSecured);  
    	    return isSecured; // All paths not listed in openApiEndpoints are secured
    	};



}



//"/owner/**","/manager/**","receptionist/**","inventory/**","room/**",
