package com.digital.mstransactionbank.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIDocument {

    @Bean
    public OpenAPI microserviceOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Digital Transaction Bank API")
                        .description("Digital Transaction Bank API")
                        .version("v1.0.0"));
    }
}
