package com.erich.dev.springbootapirestwebflux.controller;

import com.erich.dev.springbootapirestwebflux.entity.Product;
import com.erich.dev.springbootapirestwebflux.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Product>>> list(){
      return   Mono.just(ResponseEntity.ok().body(productService.getAllProducts()));
    }
    @GetMapping("/page")
    public Mono<Page<Product>> findAllPage(Pageable pageable,@RequestParam(name = "isPage")  boolean isPageable){
        return productService.findAllPage(pageable, isPageable);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Product>> findById(@PathVariable String id){
        return productService.findProductById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/name/{name}")
    public Mono<ResponseEntity<Product>> findByName(@PathVariable String name){
        return productService.findProductByName(name)
                .map(ResponseEntity::ok);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload/{id}")
    public Mono<ResponseEntity<Product>> upload(@PathVariable String id, @RequestPart FilePart file){
        return productService.uploadImage(id, file)
                .map(ResponseEntity::ok);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Mono<ResponseEntity<Product>> save(@Valid @RequestBody Mono<Product> monoProduct){
      return  monoProduct.flatMap(product -> productService.saveProduct(product)
              .map(p -> ResponseEntity.created(URI.create("/v1/api/products/".concat(p.getId())))
                      .body(p)));

    }
    @PostMapping("/v1")
    public Mono<ResponseEntity<Product>> savev1(@Valid @RequestBody Product monoProduct){
        return  productService.saveProduct(monoProduct)
                .map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public Mono<ResponseEntity<Product>> update(@PathVariable String id, @RequestBody Product product){
        return productService.updateProduct(id, product)
                .map(p -> ResponseEntity.created(URI.create("/v1/api/products/".concat(p.getId()))).body(p));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        return productService.deleteProduct(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
