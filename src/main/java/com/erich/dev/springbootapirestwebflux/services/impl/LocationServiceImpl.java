package com.erich.dev.springbootapirestwebflux.services.impl;

import com.erich.dev.springbootapirestwebflux.dto.LocationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class LocationServiceImpl {

    private final WebClient webClient;

    private final StringRedisTemplate stringRedisTemplate;
    public Flux<LocationDto> findAll(List<Long> ids){

        String joinIds = ids.stream().map(String::valueOf).collect(Collectors.joining(","));

        Flux<LocationDto> locationDtoFlux = webClient.get()
                .uri("/location/{ids}", joinIds)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(LocationDto.class);


        locationDtoFlux.subscribe(locationDto ->
                stringRedisTemplate.opsForValue().set("location:" + locationDto.id(), convertToJson(locationDto), Duration.ofMinutes(5))
        );

        return locationDtoFlux;
    }

    private String convertToJson(LocationDto locationDto) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(locationDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir LocationDto a JSON", e);
        }
    }
}
