package me.foroauth2.common.config;

import me.foroauth2.common.jwt.JwtAuthFilter;
import me.foroauth2.common.jwt.JwtExceptionHandlerFilter;
import me.foroauth2.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .httpBasic(HttpBasicConfigurer::disable) //HTTP 기본인증 비활성화
                .csrf(CsrfConfigurer::disable)           //CSRF 보호기능 비활성화
                .cors(security -> {      //corsConfigurationSource() 메서드에서 CORS 구성을 설정합니다.
                    security.configurationSource(corsConfigurationSource());
                })
                .sessionManagement((sessionManagement)->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
                        .requestMatchers("/oauth2/login-page/google").permitAll()
                        .requestMatchers("/oauth2/login/google").permitAll()
                        .requestMatchers("/oauth2/test").hasRole("USER")
                        .requestMatchers("/oauth2/test1").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtExceptionHandlerFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint));

        return httpSecurity.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() { //CORS 구성 반환 메서드
        CorsConfiguration configuration = new CorsConfiguration(); // CORS 구성을 나타낼 인스턴스

        configuration.setAllowedOrigins(List.of("*")); //모든 출처에서의 요청 허용
        configuration.setAllowedMethods(List.of("*")); // 모든 HTTP 메서드를 허용
        configuration.addAllowedOrigin("*"); // 모든 출처에서의 요청 허용
        configuration.addAllowedHeader("*");// 모든 헤더 허용
        configuration.addAllowedMethod("*"); // 모든  HTTP 메서드 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // URL 패턴별로 CorsConfiguration을 관리
        source.registerCorsConfiguration("/**", configuration); // '/**' 패턴에 대한 CorsConfiguration을 등록. 모든 요청에 대해 위에서 설정한 cors 구성이 적용됨을 의미
        return source;
    }

}
