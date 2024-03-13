package com.erich.dev.springbootapirestwebflux.config;

import com.erich.dev.springbootapirestwebflux.util.PageableHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
public class PageWebConfig implements WebFluxConfigurer {

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
      configurer.addCustomResolver(new PageableHandlerMethodArgumentResolver());
    }
}
