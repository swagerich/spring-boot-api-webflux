package com.erich.dev.springbootapirestwebflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ProductHandlerRouters handler) {
        return route(GET("/api/v2/products"), handler::listar)
                .andRoute(GET("/api/v2/products/{id}"), handler::ver)
                .andRoute(POST("/api/v2/products"), handler::create)
                .andRoute(PUT("/api/v2/products/{id}"), handler::update)
                .andRoute(DELETE("/api/v2/products/{id}"), handler::delete)
                .andRoute(POST("/api/v2/products/{id}"), handler::upload)
                .andRoute(GET("/api/v2/products/name/{name}"), handler::verName);
    }
}
