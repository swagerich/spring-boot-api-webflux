package com.erich.dev.springbootapirestwebflux.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "products")
@Document(collection = "products")
public class Product implements Serializable {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @NotNull
    private Category category;

    private String photo;
}
