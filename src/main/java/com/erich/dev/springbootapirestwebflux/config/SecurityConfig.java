package com.erich.dev.springbootapirestwebflux.config;

import com.erich.dev.springbootapirestwebflux.security.jwt.JwtFilter;
import com.erich.dev.springbootapirestwebflux.security.jwt.SecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig implements WebFluxConfigurer {

    private final SecurityContextRepository securityContextRepository;

    public SecurityConfig(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, JwtFilter jwtFilter){
        http
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers("/auth/**").permitAll()
                                .anyExchange().authenticated()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .addFilterAfter(jwtFilter, SecurityWebFiltersOrder.FIRST)
                .securityContextRepository(securityContextRepository)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE","PATH")
                .allowedOriginPatterns("http://localhost:4200")
                .allowCredentials(true);
        WebFluxConfigurer.super.addCorsMappings(registry);
    }
}
