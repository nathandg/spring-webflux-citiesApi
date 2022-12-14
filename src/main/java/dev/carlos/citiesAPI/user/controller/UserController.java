package dev.carlos.citiesAPI.user.controller;

import dev.carlos.citiesAPI.user.models.requests.updated.UserRequestChangeUserName;
import dev.carlos.citiesAPI.user.models.requests.login.UserRequestLogin;
import dev.carlos.citiesAPI.user.models.requests.register.UserRequestRegister;
import dev.carlos.citiesAPI.user.models.responses.UserLoginResponse;
import dev.carlos.citiesAPI.user.models.responses.UserResponse;
import dev.carlos.citiesAPI.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService userService;

    //for testing findAll Users
    @GetMapping("/users")
    public Flux<UserResponse> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping
    public String getUserById()
    {
        return "Hello World";
    }

    @PostMapping
    public Mono<UserResponse> createUser(@RequestBody UserRequestRegister userRequestRegister){
        return userService.save(userRequestRegister);
    }

    @PostMapping("/login")
    public Mono<UserLoginResponse> login(@RequestBody UserRequestLogin userRequestLogin){
        return userService.login(userRequestLogin);
    }

    @PutMapping("/changeUsername")
    public Mono<UserResponse> changeUsername(@RequestBody UserRequestChangeUserName userChangeUsernameRequest){
        return userService.changeUsername(userChangeUsernameRequest);
    }
}
