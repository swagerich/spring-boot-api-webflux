package com.erich.dev.springbootapirestwebflux.controller;

import com.erich.dev.springbootapirestwebflux.dto.LoginDto;
import com.erich.dev.springbootapirestwebflux.dto.RegistryUserDto;
import com.erich.dev.springbootapirestwebflux.dto.TokenDto;
import com.erich.dev.springbootapirestwebflux.entity.User;
import com.erich.dev.springbootapirestwebflux.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/registry", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<User>> registry(@RequestBody RegistryUserDto registryUserDto){
    return userService.registry(registryUserDto)
            .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user));
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TokenDto>> registry(@RequestBody LoginDto loginDto){
        return userService.login(loginDto)
                .map(token -> ResponseEntity.status(HttpStatus.CREATED).body(token));
    }
}
