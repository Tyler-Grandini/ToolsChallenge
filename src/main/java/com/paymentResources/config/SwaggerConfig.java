package com.paymentResources.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
   info = @Info(
      title = "Payment API",
      version = "1.0",
      description = "Collection of Apis for Payment"
   )
)
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
               .group("public-api")
               .pathsToMatch("/**")
               .build();
    }
}