package com.erich.dev.springbootapirestwebflux.controller;

import com.erich.dev.springbootapirestwebflux.entity.Category;
import com.erich.dev.springbootapirestwebflux.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Category>>> categories(){
        return Mono.just(ResponseEntity.ok(categoryService.findAllCategories()));
    }

    @PostMapping
    public Mono<ResponseEntity<Category>> createCategory(@RequestBody Category category){
        return categoryService.saveCategory(category).map(
                response -> ResponseEntity.status(HttpStatus.CREATED).body(response)
        );
    }
}
