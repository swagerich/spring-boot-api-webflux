package com.erich.dev.springbootapirestwebflux.repository;

import com.erich.dev.springbootapirestwebflux.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String>{

    Mono<User> findByUserNameOrMail(String userName, String mail);
}
