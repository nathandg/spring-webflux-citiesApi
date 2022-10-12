package dev.carlos.citiesAPI.regioes.controller;

import dev.carlos.citiesAPI.regioes.models.requests.CityRequest;
import dev.carlos.citiesAPI.regioes.models.responses.CityResponse;
import dev.carlos.citiesAPI.regioes.services.implementations.CityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/regions")
public class RegionsController {

    @Autowired
    private CityServiceImpl cityService;

    @GetMapping("/all")
    public Flux<CityResponse> findAllCities() {
        return cityService.findAllCities();
    }

    @GetMapping("/{cityRegion}")
    public Flux<CityResponse> findCitiesByRegion(@PathVariable String cityRegion) {
        System.out.println(cityRegion);
        return cityService.findCitiesByRegion(cityRegion);
    }

    @PostMapping("/")
    public Flux<CityResponse> saveCity(@RequestBody CityRequest cityRequest) {
        return cityService.createCity(cityRequest);
    }
}
