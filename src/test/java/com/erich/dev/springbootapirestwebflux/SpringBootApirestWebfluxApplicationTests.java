package com.erich.dev.springbootapirestwebflux;

import com.erich.dev.springbootapirestwebflux.entity.Category;
import com.erich.dev.springbootapirestwebflux.entity.Product;
import com.erich.dev.springbootapirestwebflux.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootApirestWebfluxApplicationTests {


    @Autowired
    private  WebTestClient client;

    @Autowired
    private ProductService productService;

    @Test
    void listarTest() {
        client.get().uri("/api/v2/products")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Product.class).consumeWith(response ->{
                    List<Product> responseBody = response.getResponseBody();
                    Assertions.assertNotNull(responseBody);
                    responseBody.forEach(p -> {
                        System.out.println(p.getName());
                    });
                    Assertions.assertEquals(4, responseBody.size());
                });
    }

    @Test
    void ver() {
        client.get().uri("/api/v2/products/name/{name}", "CPU")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Product.class).consumeWith(response ->{
                    Product responseBody = response.getResponseBody();
                    Assertions.assertNotNull(responseBody);
                    Assertions.assertEquals("CPU", responseBody.getName());
                });
    }


    @Test
    void create(){
        Category category = new Category(null, "Electronics");
        Product product = new Product(null, "Monitor",new BigDecimal("250.0"),null,category,null);
        client.post().uri("/api/v2/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange().expectStatus().isCreated().expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Product.class).consumeWith(response ->{
                    Product responseBody = response.getResponseBody();
                    Assertions.assertNotNull(responseBody);
                    Assertions.assertEquals("Monitor", responseBody.getName());
                    Assertions.assertEquals(new BigDecimal("250.0"), responseBody.getPrice());
                });
    }

    @Test
    void update(){
        Category category = new Category(null, "Electronics");
        Product product = new Product(null, "Erich",new BigDecimal("500.0"),null,category,null);

        Product productName = productService.findProductByName("CPU").block();
        client.put().uri("/api/v2/products/{id}", productName.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange().expectStatus().isCreated().expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Product.class).consumeWith(response ->{
                    Product responseBody = response.getResponseBody();
                    Assertions.assertNotNull(responseBody);
                    Assertions.assertEquals("Erich", responseBody.getName());
                    Assertions.assertEquals(new BigDecimal("500.0"), responseBody.getPrice());
                });
    }

    @Test
    void delete(){
        Product productName = productService.findProductByName("CPU").block();
        if(productName != null){
            client.delete().uri("/api/v2/products/{id}", productName.getId())
                    .exchange().expectStatus().isNoContent().expectBody().isEmpty();
        }

    }

}
