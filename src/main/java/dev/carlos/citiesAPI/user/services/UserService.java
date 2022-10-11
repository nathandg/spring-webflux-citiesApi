package dev.carlos.citiesAPI.user.services;

import dev.carlos.citiesAPI.user.models.requests.login.UserRequestLogin;
import dev.carlos.citiesAPI.user.models.requests.register.UserRequestRegister;
import dev.carlos.citiesAPI.user.models.responses.UserLoginResponse;
import dev.carlos.citiesAPI.user.models.responses.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponse> save(UserRequestRegister userRequestRegister);

    Flux<UserResponse> findAllUsers();

    Mono<UserLoginResponse> login(UserRequestLogin userRequestLogin);

    Mono<Boolean> isAuthorized(String token);

}
