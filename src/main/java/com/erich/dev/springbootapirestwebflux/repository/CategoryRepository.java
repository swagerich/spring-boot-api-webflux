package com.erich.dev.springbootapirestwebflux.repository;

import com.erich.dev.springbootapirestwebflux.entity.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
