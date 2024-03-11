package com.erich.dev.springbootapirestwebflux.services;

import com.erich.dev.springbootapirestwebflux.dto.LoginDto;
import com.erich.dev.springbootapirestwebflux.dto.RegistryUserDto;
import com.erich.dev.springbootapirestwebflux.dto.TokenDto;
import com.erich.dev.springbootapirestwebflux.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {


    Mono<TokenDto> login(LoginDto loginDto);


    Mono<User> registry(RegistryUserDto registryUserDto);

}
