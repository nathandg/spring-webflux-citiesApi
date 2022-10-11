package dev.carlos.citiesAPI.user.services.implementation;

import dev.carlos.citiesAPI.user.domain.User;
import dev.carlos.citiesAPI.user.domain.UserRepository;
import dev.carlos.citiesAPI.user.models.requests.register.UserRequestRegister;
import dev.carlos.citiesAPI.user.models.responses.UserResponse;
import dev.carlos.citiesAPI.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserResponse> save(UserRequestRegister userRequestRegister) {

        //verify if user already exists
        Mono<User> byUserEmail = userRepository.findByUserEmail(userRequestRegister.getUserEmail());
        ModelMapper modelMapper = new ModelMapper();

        return byUserEmail
                .flatMap(user -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
                })
                .switchIfEmpty(Mono.defer(() -> {
                    User userToSave = modelMapper.map(userRequestRegister, User.class);
                    return userRepository.save(userToSave);
                }))
                .map(user -> modelMapper.map(user, UserResponse.class));
    }

    @Override
    public Flux<UserResponse> findAllUsers() {
        return userRepository.findAll().map(user -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(user, UserResponse.class);
        });
    }

}
