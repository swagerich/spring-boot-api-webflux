package com.erich.dev.springbootapirestwebflux.repository.custom.impl;

import com.erich.dev.springbootapirestwebflux.entity.Product;
import com.erich.dev.springbootapirestwebflux.repository.custom.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<Product> findAllWithPageable(Pageable pageable, boolean isPageable) {

        return Flux.defer(() -> {
            if (isPageable) {
                return mongoTemplate.find(buildQueryWithFilters(pageable), Product.class);
            }else {
                return mongoTemplate.findAll(Product.class, "products");
            }});

    }


    @Override
    public Mono<Long> countAllWithPageable(Pageable pageable) {
        return mongoTemplate.count(buildQueryWithFilters(pageable), Product.class);
    }

    private Query buildQueryWithFilters(Pageable pageable) {
        final Query query = new Query();
        if (pageable != null) {
            query.with(pageable);
        }
        return query;
    }
}
