package com.erich.dev.springbootapirestwebflux.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.StringTemplate.STR;

class Employe{
    int id;
    String name;

    Integer salary;

    String department;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return STR."Employe{id=\{id}, name='\{name}\{'\''}, salary=\{salary}, department='\{department}\{'\''}\{'}'}";
    }
}
public class TestMain {

     void main() throws IOException {

 /*       String jsonArray = "[{\"id\":1,\"name\":\"Alice\",\"salary\":60000,\"department\":\"IT\"}," +
                "{\"id\":2,\"name\":\"Bob\",\"salary\":75000,\"department\":\"HR\"}," +
                "{\"id\":3,\"name\":\"Charlie\",\"salary\":48000,\"department\":\"IT\"}," +
                "{\"id\":4,\"name\":\"David\",\"salary\":90000,\"department\":\"Finance\"}," +
                "{\"id\":5,\"name\":\"Eva\",\"salary\":55000,\"department\":\"Finance\"}]";


        ObjectMapper objectMapper = new ObjectMapper();

        List<Employe> employes = objectMapper.readValue(jsonArray, new TypeReference<>() {
        });

        Flux<Employe> flux = Flux.fromIterable(employes);
        flux.filter(salary -> salary.salary > 50000)
                .sort(Comparator.comparing(Employe::getSalary).reversed())
                .groupBy(Employe::getDepartment)
                .flatMap(Flux::collectList)
                .collectMap(e -> e.getFirst().getDepartment(), e -> e)
                .doOnNext(System.out::println).subscribe();
*/

        Flux<String> names = Flux.just("Alice", null, "Charlie", "David", "Eva");

        names.handle((name,sink) ->{
            if(name != null){
                sink.next(name);
                return;
            }
            sink.error(new IllegalArgumentException("Null value"));
        }).subscribe(x -> System.out.println(x),error -> System.out.println(error.getMessage()));
     }


}
