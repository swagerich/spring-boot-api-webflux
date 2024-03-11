package com.erich.dev.springbootapirestwebflux.services;

import com.erich.dev.springbootapirestwebflux.entity.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<Category> findAllCategories();

    Mono<Category> findByIdCategory(String id);

    Mono<Category> saveCategory(Category category);
}
