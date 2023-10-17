package recipes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.model.UserRegistrationRequest;
import recipes.persistence.UserEntity;
import recipes.service.UserService;
import recipes.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final UserServiceImpl userServiceImpl;

    @PostMapping("register")
    ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {

        if (userService.emailExists(userRegistrationRequest.getEmail())) {
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }

        userService.saveUser(userRegistrationRequest);
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @GetMapping("users")
    List<UserEntity> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }
}
