package com.erich.dev.springbootapirestwebflux.services.impl;


import com.erich.dev.springbootapirestwebflux.entity.Product;
import com.erich.dev.springbootapirestwebflux.exception.NotFoundException;
import com.erich.dev.springbootapirestwebflux.repository.CategoryRepository;
import com.erich.dev.springbootapirestwebflux.repository.ProductRepository;
import com.erich.dev.springbootapirestwebflux.services.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    @Cacheable("getAllProducts")
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
//    @Cacheable("findAllPage")
    public Mono<Page<Product>> findAllPage(Pageable pageable, boolean isPageable) {
        return Mono.defer(() -> {
            if (isPageable) {
                return productRepository.findAllWithPageable(pageable, true)
                        .collectList()
                        .zipWith(productRepository.countAllWithPageable(pageable))
                        .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()))
                        .map(page -> page);
            } else {
                return productRepository.findAll()
                        .collectList().map( page -> new PageImpl<>(page, Pageable.unpaged(), 0));
            }
        });
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
