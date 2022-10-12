package dev.carlos.citiesAPI.regioes.services;

import dev.carlos.citiesAPI.regioes.models.requests.CityRequest;
import dev.carlos.citiesAPI.regioes.models.responses.CityResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CityService {
    Flux<CityResponse> findAllCities();
    Flux<CityResponse> createCity(CityRequest cityRequest);
    Flux<CityResponse> findCitiesByRegion(String region);
}
