package com.erich.dev.springbootapirestwebflux;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;


@SpringBootApplication
public class SpringBootApirestWebfluxApplication implements CommandLineRunner {


//    private final ProductRepository productRepository;
//
//    private final CategoryRepository categoryRepository;

    private final ReactiveMongoTemplate reactiveMongoTemplate;


    public SpringBootApirestWebfluxApplication(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApirestWebfluxApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {

//        reactiveMongoTemplate.dropCollection("products").subscribe();
//        reactiveMongoTemplate.dropCollection("categories").subscribe();
//        reactiveMongoTemplate.dropCollection("users").subscribe();
//
//        Category category = new Category(null, "Electronics");
//        Category category2 = new Category(null, "Computers");
//
//        Product product = new Product(null, "TV Panasonic", new BigDecimal("250.00"), new Date(),category,null);
//        Product product2 = new Product(null, "CPU", new BigDecimal("150.00"), new Date(),category,null);
//        Product product3 = new Product(null, "Audifono", new BigDecimal("350.00"), new Date(),category2,null);
//        Product product4 = new Product(null, "Teclado Legion", new BigDecimal("270.00"), new Date(),category2,null);
//
//        categoryRepository.saveAll(Flux.just(category, category2)).thenMany(
//                Flux.just(product,product2, product3, product4)
//                        .flatMap(productRepository::save)
//        ).subscribe();
    }
}
