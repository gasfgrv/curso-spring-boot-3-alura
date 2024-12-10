package med.voll.api.infra.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringdocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        var securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        var component = new Components()
                .addSecuritySchemes("auth-token", securityScheme);

        var license = new License()
                .name("Apache 2.0")
                .url("http://voll.med/api/licenca");

        var contact = new Contact()
                .name("Time Backend")
                .email("backend@voll.med");

        var info = new Info()
                .title("Voll.med API")
                .description(
                        "API Rest da aplicação Voll.med, contendo as funcionalidades de CRUD de médicos e de pacientes, além de agendamento e cancelamento de consultas")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .components(component)
                .info(info);
    }

}
