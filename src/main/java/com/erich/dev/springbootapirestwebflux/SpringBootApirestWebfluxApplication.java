package com.erich.dev.springbootapirestwebflux;

import com.erich.dev.springbootapirestwebflux.entity.Category;
import com.erich.dev.springbootapirestwebflux.entity.Product;
import com.erich.dev.springbootapirestwebflux.repository.CategoryRepository;
import com.erich.dev.springbootapirestwebflux.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class SpringBootApirestWebfluxApplication implements CommandLineRunner {


    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ReactiveMongoTemplate reactiveMongoTemplate;


    public SpringBootApirestWebfluxApplication(ProductRepository productRepository, CategoryRepository categoryRepository, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringBootApirestWebfluxApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {

        reactiveMongoTemplate.dropCollection("products").subscribe();
        reactiveMongoTemplate.dropCollection("categories").subscribe();

        Category category = new Category(null, "Electronics");
        Category category2 = new Category(null, "Computers");

        Product product = new Product(null, "TV Panasonic", new BigDecimal("250.00"), new Date(),category,null);
        Product product2 = new Product(null, "CPU", new BigDecimal("150.00"), new Date(),category,null);
        Product product3 = new Product(null, "Audifono", new BigDecimal("350.00"), new Date(),category2,null);
        Product product4 = new Product(null, "Teclado Legion", new BigDecimal("270.00"), new Date(),category2,null);

        categoryRepository.saveAll(Flux.just(category, category2)).thenMany(
                Flux.just(product,product2, product3, product4)
                        .flatMap(productRepository::save)
        ).subscribe();
    }
}
