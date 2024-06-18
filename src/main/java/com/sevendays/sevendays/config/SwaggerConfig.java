package com.sevendays.sevendays.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customApi() {
        return new OpenAPI()
                .info(new Info()
                    .title("Seven Days of Code - API")
                    .description("API para gerenciamento de super-heróis"))
                .addTagsItem(new Tag().name("Superherois").description("Operações relacionadas a super-heróis"));
    }

    @Bean
    public OpenApiCustomizer customizeOpenAPI() {
        return openApi -> {
            Paths paths = openApi.getPaths();

            // Move os métodos desejados para a tag "Superherois"
            if (paths.containsKey("/superherois")) {
                PathItem pathItem = paths.get("/superherois");
                if (pathItem.getGet() != null) {
                    pathItem.getGet().addTagsItem("Superherois");
                }
                if (pathItem.getPost() != null) {
                    pathItem.getPost().addTagsItem("Superherois");
                }
                if (pathItem.getPut() != null) {
                    pathItem.getPut().addTagsItem("Superherois");
                }
                if (pathItem.getDelete() != null) {
                    pathItem.getDelete().addTagsItem("Superherois");
                }
            }

            if (paths.containsKey("/superherois/search")) {
                PathItem pathItem = paths.get("/superherois/search");
                if (pathItem.getGet() != null) {
                    pathItem.getGet().addTagsItem("Superherois");
                }
            }

            // Remover métodos do "superheroi-search-controller"
            if (paths.containsKey("/superherois/{id}")) {
                PathItem pathItem = paths.get("/superherois/{id}");
                pathItem.setGet(null);
                pathItem.setPut(null);
                pathItem.setDelete(null);
                pathItem.setPatch(null);
            }

            // Remover métodos não desejados para /profile e /profile/superherois
            if (paths.containsKey("/profile")) {
                PathItem pathItem = paths.get("/profile");
                pathItem.setGet(null);
            }

            if (paths.containsKey("/profile/superherois")) {
                PathItem pathItem = paths.get("/profile/superherois");
                pathItem.setGet(null);
            }
        };
    }
}