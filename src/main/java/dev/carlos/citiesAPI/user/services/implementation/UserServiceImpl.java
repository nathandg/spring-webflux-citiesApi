package dev.carlos.citiesAPI.user.services.implementation;

import dev.carlos.citiesAPI.user.domain.User;
import dev.carlos.citiesAPI.user.domain.UserRepository;
import dev.carlos.citiesAPI.user.models.requests.updated.UserRequestChangeUserName;
import dev.carlos.citiesAPI.user.models.requests.login.UserRequestLogin;
import dev.carlos.citiesAPI.user.models.requests.register.UserRequestRegister;
import dev.carlos.citiesAPI.user.models.responses.UserLoginResponse;
import dev.carlos.citiesAPI.user.models.responses.UserResponse;
import dev.carlos.citiesAPI.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
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

    @Override
    public Mono<UserLoginResponse> login(UserRequestLogin userRequestLogin) {

        ModelMapper modelMapper = new ModelMapper();

        return userRepository.findByUserEmail(userRequestLogin.getUserEmail())
                .switchIfEmpty(Mono.defer(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }))

                //what is a different between .map and .flatMap? in this case, I need to use .map or .flatmap?
                .map(user -> {

                    if (user.getUserEmail().equals(userRequestLogin.getUserEmail()) && user.getUserPassword().equals(userRequestLogin.getUserPassword())){
                        //generate token
                        user.setUserToken(UUID.randomUUID().toString());
                        //save token
                        userRepository.save(user).subscribe();
                        //return a responseLogin
                        return modelMapper.map(user, UserLoginResponse.class);

                    } else {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                    }
                });
    }

    @Override
    public Mono<UserResponse> changeUsername(UserRequestChangeUserName userChangeUsernameRequest) {

        ModelMapper modelMapper = new ModelMapper();

        return userRepository.findByUserToken(userChangeUsernameRequest.getUserToken())
                .switchIfEmpty(Mono.defer(() -> {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }))
                .map(user -> {
                    user.setUserName(userChangeUsernameRequest.getUserName());
                    userRepository.save(user).subscribe();
                    return modelMapper.map(user, UserResponse.class);
                });

    }

}
