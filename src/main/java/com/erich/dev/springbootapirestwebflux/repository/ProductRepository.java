package com.erich.dev.springbootapirestwebflux.repository;

import com.erich.dev.springbootapirestwebflux.entity.Product;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Mono<Product> findByName(String name);
}
