package com.erich.dev.springbootapirestwebflux.controller;

import com.erich.dev.springbootapirestwebflux.dto.LocationDto;
import com.erich.dev.springbootapirestwebflux.services.impl.LocationServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class LocationController {

    private final LocationServiceImpl locationService;

    public LocationController(LocationServiceImpl locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations/{ids}")
    public Flux<LocationDto> all(@PathVariable List<Long> ids){
    return locationService.findAll(ids);
    }
}
