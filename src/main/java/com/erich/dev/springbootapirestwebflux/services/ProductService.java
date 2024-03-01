package com.erich.dev.springbootapirestwebflux.services;

import com.erich.dev.springbootapirestwebflux.entity.Category;
import com.erich.dev.springbootapirestwebflux.entity.Product;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> getAllProducts();
    Flux<Product> findAllWithNameToUpperCase();

    Mono<Product> findProductById(String id);

    Mono<Product> findProductByName(String name);


    Mono<Product> uploadImage(String id, FilePart file);
    Mono<Product> saveProduct(Product product);

    Mono<Product> updateProduct(String id, Product product);

    Mono<Void> deleteProduct(String id);

    Flux<Category> findAllCategories();

    Mono<Category> findByIdCategory(String id);

    Mono<Category> saveCategory(Category category);
}
