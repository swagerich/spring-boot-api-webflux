package com.erich.dev.springbootapirestwebflux.services.impl;

import com.erich.dev.springbootapirestwebflux.dto.LoginDto;
import com.erich.dev.springbootapirestwebflux.dto.RegistryUserDto;
import com.erich.dev.springbootapirestwebflux.dto.TokenDto;
import com.erich.dev.springbootapirestwebflux.entity.User;
import com.erich.dev.springbootapirestwebflux.entity.auth.Role;
import com.erich.dev.springbootapirestwebflux.exception.BadRequestException;
import com.erich.dev.springbootapirestwebflux.repository.UserRepository;
import com.erich.dev.springbootapirestwebflux.security.jwt.JwtProvider;
import com.erich.dev.springbootapirestwebflux.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<TokenDto> login(LoginDto loginDto) {
        return userRepository.findByUserNameOrMail(loginDto.getUserName(), loginDto.getUserName())
                .filter(user -> passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
                .map(jwtProvider::generateToken)
                .map(token -> TokenDto.builder().token(token).bearer("Bearer").build())
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }

    @Override
    public Mono<User> registry(RegistryUserDto UserDto) {

        Mono<RegistryUserDto> registryUserDto = Mono.just(UserDto);

        return registryUserDto.flatMap(userDto -> {
            User user;
            if (userDto.getUserName().contains("administrador") && userDto.getEmail().contains("administrador")) {
                user = User.builder().userName(userDto.getUserName().toLowerCase())
                        .mail(userDto.getEmail())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .roles(STR."\{Role.ROLE_ADMIN.name()}")
                        .build();
            } else {
                user = User.builder().userName(userDto.getUserName().toLowerCase())
                        .mail(userDto.getEmail())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .roles(STR."\{Role.ROLE_USER.name()}")
                        .build();
            }
            return userRepository.findByUserNameOrMail(user.getUsername(), user.getMail()).hasElement()
                    .flatMap(exist -> exist ? Mono.error(new BadRequestException("User or email already  exists")) : userRepository.save(user));
        });
//        User user = User.builder().userName(registryUserDto.getUserName())
//                .mail(registryUserDto.getEmail())
//                .password(passwordEncoder.encode(registryUserDto.getPassword()))
//                .roles(STR."\{Role.ROLE_ADMIN.name()}, \{Role.ROLE_USER.name()}")
//                .build();

//        return userRepository.findByUserNameOrMail(user.getUsername(), user.getMail()).hasElement()
//                .flatMap(exist -> exist ? Mono.error(new RuntimeException("User or email already  exists")) : userRepository.save(user));
    }
}
