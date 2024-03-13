package com.erich.dev.springbootapirestwebflux.repository.custom;

import com.erich.dev.springbootapirestwebflux.entity.Product;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public interface ProductRepositoryCustom {

    Flux<Product> findAllWithPageable( Pageable pageable , boolean isPage);

    Mono<Long> countAllWithPageable(final Pageable pageable);
}
