package com.erich.dev.springbootapirestwebflux;

import com.erich.dev.springbootapirestwebflux.entity.Product;
import com.erich.dev.springbootapirestwebflux.services.ProductService;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductHandlerRouters {

    private final ProductService productService;

    private final Validator validator;


    public ProductHandlerRouters(ProductService productService, Validator validator) {
        this.productService = productService;
        this.validator = validator;
    }

    public Mono<ServerResponse> listar(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getAllProducts(), Product.class);
    }

    public Mono<ServerResponse> ver(ServerRequest request){
        String id = request.pathVariable("id");
        return productService.findProductById(id)
                .flatMap(p -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(p)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> verName(ServerRequest request){
        String name = request.pathVariable("name");
        return productService.findProductByName(name)
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(p)));
    }

    public Mono<ServerResponse> upload(ServerRequest request){
        String id = request.pathVariable("id");
       return request.multipartData().map(multipart -> multipart.toSingleValueMap().get("file"))
                .cast(FilePart.class)
                .flatMap(file -> productService.uploadImage(id, file))
                .flatMap(p -> ServerResponse.created(request.uriBuilder().path("/api/v2/products/{id}").build(p.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(p)));
    }


    public Mono<ServerResponse> create(ServerRequest request){
       return request.bodyToMono(Product.class)
                .flatMap(product -> {
                    BindingResult erros = new BeanPropertyBindingResult(product, Product.class.getName());
                    validator.validate(product, erros);
                    if(erros.hasErrors()){
                        return Mono.error(new WebExchangeBindException(new MethodParameter(this.getClass().getMethods()[0], 0), erros));
                    }else{
                        return productService.saveProduct(product);
                    }
                })
                .flatMap(p -> ServerResponse.created(request.uriBuilder().path("/{id}").build(p.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(p)));
    }

    public Mono<ServerResponse> update(ServerRequest request){
        String id = request.pathVariable("id");
        return request.bodyToMono(Product.class)
                .flatMap(product -> productService.updateProduct(id, product))
                .flatMap(p -> ServerResponse.created(request.uriBuilder().path("/{id}").build(p.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(p)));
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        String id = request.pathVariable("id");
        return productService.deleteProduct(id)
                .then(ServerResponse.noContent().build());
    }
}
