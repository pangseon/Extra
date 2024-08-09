package com.example.extra.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer";
    private static final String JWT = "JWT";


    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
            .version("v1.0.0")
            .title("Extra API")
            .description("Extra API Description");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT);
        Components components = new Components()
            .addSecuritySchemes(JWT, new SecurityScheme()
                .name(JWT)
                .type(SecurityScheme.Type.HTTP)
                .scheme(BEARER_TOKEN_PREFIX)
                .bearerFormat(JWT));

        return new OpenAPI()
            .info(info)
            .addSecurityItem(securityRequirement)
            .components(components);
    }

}
