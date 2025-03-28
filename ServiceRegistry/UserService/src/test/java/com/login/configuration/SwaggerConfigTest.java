package com.login.configuration;
 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springdoc.core.models.GroupedOpenApi;
import io.swagger.v3.oas.models.OpenAPI;
 
import static org.junit.jupiter.api.Assertions.*;
 
@SpringBootTest
public class SwaggerConfigTest {
 
    @Autowired
    private ApplicationContext context;
 
    private GroupedOpenApi publicApi;
 
    @BeforeEach
    void setUp() {
        publicApi = context.getBean(GroupedOpenApi.class);
    }
 
    @Test
    void testPublicApiBean() {
        assertNotNull(publicApi, "publicApi bean should not be null");
 
        String[] expectedPaths = new String[]{"/**"};
        String[] actualPaths = publicApi.getPathsToMatch().toArray(new String[0]);
 
        assertArrayEquals(expectedPaths, actualPaths, "Paths should match /**");
    }
 
    @Test
    void testCustomOpenAPIConfig() {
        OpenAPI openAPI = context.getBean(OpenAPI.class);
 
        assertNotNull(openAPI, "Custom OpenAPI bean should be present");
        assertNotNull(openAPI.getComponents(), "OpenAPI components should not be null");
        assertNotNull(openAPI.getComponents().getSecuritySchemes().get("bearerAuth"), "Bearer authentication should be configured");
    }
}
