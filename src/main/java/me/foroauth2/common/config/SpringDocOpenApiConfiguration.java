package me.foroauth2.common.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

//OpenAPI 정의에 서버 정보를 추가하는데 사용
//servers = OpenAPI 문서에 포함될 서버 정보를 나타내는 속성
//@Server = OpenAPI 문서에 추가할 서버를 나타내는 어노테이션
//url = "/": 서버의 URL을 지정합니다. 이 예시에서는 기본 URL을 "/"로 지정하였습니다.
//description = 서버에대한 설명
@OpenAPIDefinition(servers = {@Server(url = "/", description = "default generated url")})
@Configuration
public class SpringDocOpenApiConfiguration {

    @Bean
    public OpenAPI springOpenAPI() {
        //API 요청헤더에 인증정보 포함
        //보안 요구 사항을 나타내는 SecurityRequireent 객체 생성
        SecurityRequirement securityRequirement = new SecurityRequirement();
        //JWT를 사용하여 인증하는 보안 요구사항 추가
        securityRequirement.addList("JWT");

        //Security 스키마 설정
        //JWT를 사용하는 Bearer 토큰 기반의 보안 스키마 정의
        //이는 HTTP 헤더에 JWT를 포함시켜 인증하는 방식입니다.
        SecurityScheme bearerAuth = new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        //OpenAPI 객체를 생성하여 반환합니다.
        return new OpenAPI()
                //Security 인증 컴포넌트 설정
                .components(new Components().addSecuritySchemes("JWT", bearerAuth))
                //API 마다 Security 인증 컴포넌트 설정
                .addSecurityItem(securityRequirement)
                .info(info());
    }

    private Info info(){
        return new Info()
                .title("ForOauth2 프로젝트 API Document")
                .version("0.1")
                .description("ForOAuth2 프로젝트의 API 명세서 입니다.");
    }
}