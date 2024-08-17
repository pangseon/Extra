package com.example.extra;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@OpenAPIDefinition(servers = {
    @Server(url = "${domain.url}", description = "Default Server URL")}
)
@SpringBootApplication
public class ExtraApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtraApplication.class, args);
    }

}
