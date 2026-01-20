package com.tradingsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Trading System API")
                        .description("REST API for managing trading products - Buy and Sell trading instruments")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Trading System Team")
                                .email("support@tradingsystem.com")
                                .url("https://tradingsystem.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(Arrays.asList(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.tradingsystem.com")
                                .description("Production Server"),
                        new Server()
                                .url("https://api-staging.tradingsystem.com")
                                .description("Staging Server")
                ));
    }
}
