package com.erich.dev.springbootapirestwebflux.services.impl;

import com.erich.dev.springbootapirestwebflux.entity.Category;
import com.erich.dev.springbootapirestwebflux.entity.Product;
import com.erich.dev.springbootapirestwebflux.exception.NotFoundException;
import com.erich.dev.springbootapirestwebflux.repository.CategoryRepository;
import com.erich.dev.springbootapirestwebflux.repository.ProductRepository;
import com.erich.dev.springbootapirestwebflux.services.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Flux<Product> getAllProducts() {
        Flux<Product> allProducts = productRepository.findAll();
        return allProducts;
    }

    @Override
    public Flux<Product> findAllWithNameToUpperCase() {
        Flux<Product> allProducts = productRepository.findAll().map(product -> {
            product.setName(product.getName().toUpperCase());
            return product;
        });
        ;
        return allProducts;
    }

    @Override
    public Mono<Product> findProductById(String id) {
        return productRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Product not found")));
    }

    @Override
    public Mono<Product> findProductByName(String name) {
        return productRepository.findByName(name).switchIfEmpty(Mono.error(new NotFoundException("Product not found")));
    }

    @Override
    public Mono<Product> uploadImage(String id, FilePart file) {
        return productRepository.findById(id).flatMap(p -> {
            String uniqueFileName = STR."\{UUID.randomUUID()}-\{file.filename().replace(" ", "_")
                    .replace(":", "")
                    .replace("\\", "")
                    .replace("/", "")}";
            p.setPhoto(uniqueFileName);
            if (!file.filename().isEmpty()) {
                Path path = Path.of("C:\\Users\\erick\\OneDrive\\Escritorio\\webflux").resolve(p.getPhoto()).toAbsolutePath();
                return file.transferTo(path).then(productRepository.save(p));
            }
            return Mono.empty();
        }).switchIfEmpty(Mono.error(new NotFoundException("Product not found")));
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return categoryRepository.findById(product.getCategory().getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Category not found")))
                .flatMap(category -> {
                    product.setCategory(category);
                    return productRepository.save(product);
                });
    }

    @Override
    public Mono<Product> updateProduct(String id, Product product) {
        return productRepository.findById(id)
                .flatMap(p -> {
                    p.setName(product.getName());
                    p.setPrice(product.getPrice());
                    p.setCategory(product.getCategory());
                    return productRepository.save(p);
                }).switchIfEmpty(Mono.error(new NotFoundException("Product not found")));
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Product not found"))).flatMap(x -> productRepository.deleteById(id));
    }
}
