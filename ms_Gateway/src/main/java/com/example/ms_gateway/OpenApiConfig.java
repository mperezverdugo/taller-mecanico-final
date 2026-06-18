package com.example.ms_gateway;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration

public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Server gatewayServer = new Server();
        // Se fuerza  que la URL base de las peticiones sea el Gateway
        gatewayServer.setUrl("http://localhost:8080");
        gatewayServer.setDescription("API Gateway Router Oficial");

        return new OpenAPI().servers(List.of(gatewayServer));
    }
}

