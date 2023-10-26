package com.trip.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;

@Configuration
public class OpenApiConfig {


    static {
        SpringDocUtils.getConfig()
                //.addAnnotationsToIgnore(AuthenticationPrincipal.class);
                .addAnnotationsToIgnore(AuthenticationPrincipal.class, CookieValue.class); // 해당 class 는 swagger Example Value JSON 에서 삭제함
    }

    /**
     * 1) API Grouping > ALL
     */
    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("API ALL")
                .pathsToMatch("/**")
                .build();
    }


    @Bean
    public OpenAPI openAPI(Environment environment) {

        // SecuritySecheme명
        String jwtSchemeName = "jwtAuth";

        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        // SecuritySchemes 등록
        Components components = new Components()
                        .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        Info info = new Info()
                .title("WHY Trip API")
                .version("v1.0.0")
                .description("WHY Trip");

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .addServersItem(new Server().url(environment.getProperty("server.servlet.context-path", "/"))) // https -> http 로 바뀌는것을 예방
                .components(components)
                .info(info);
    }



}