package com.fluxin.flux_in.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("FluxIn API")
                        .description("API Rest da aplicação FluxIn, contendo as funcionalidades de CRUD de estabelecimento, funcionários e procedimentos, além de agendamento e cancelamento de atendimentos.")
                        .contact(new Contact()
                                .name("Caio Vianeda")
                                .email("caio.vianwda@gmail.com"))
                );

    }
}
