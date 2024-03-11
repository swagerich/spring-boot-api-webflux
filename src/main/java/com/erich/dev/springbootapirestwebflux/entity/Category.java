package com.erich.dev.springbootapirestwebflux.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "categories")
@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    private String name;
}
