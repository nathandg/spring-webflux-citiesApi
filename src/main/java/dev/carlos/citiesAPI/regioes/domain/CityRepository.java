package dev.carlos.citiesAPI.regioes.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CityRepository extends ReactiveMongoRepository<City, String> {
    Flux<City> findByCityRegion(String cityRegion);
    Flux<City> findByCityName(String cityName);

}
