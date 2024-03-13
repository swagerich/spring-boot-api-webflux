package com.erich.dev.springbootapirestwebflux.repository;

import com.erich.dev.springbootapirestwebflux.entity.Product;

import com.erich.dev.springbootapirestwebflux.repository.custom.ProductRepositoryCustom;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<Product, String>, ProductRepositoryCustom {

    Mono<Product> findByName(String name);
}
