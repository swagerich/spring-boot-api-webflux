package com.erich.dev.springbootapirestwebflux.services.impl;

import com.erich.dev.springbootapirestwebflux.entity.Category;
import com.erich.dev.springbootapirestwebflux.repository.CategoryRepository;
import com.erich.dev.springbootapirestwebflux.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service @RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Flux<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Mono<Category> findByIdCategory(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Mono<Category> saveCategory(Category category) {
        return categoryRepository.save(category);
    }

}
