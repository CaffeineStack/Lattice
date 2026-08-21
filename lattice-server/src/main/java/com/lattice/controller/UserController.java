package com.lattice.controller;
import com.lattice.dto.LoginResponse;
import com.lattice.dto.UserRequest;
import com.lattice.model.User;
import com.lattice.service.UserService;
import jakarta.validation.Valid;
import org.mapstruct.control.MappingControl;
import com.lattice.dto.UserResponse;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.lattice.repository.UserRepository;
@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    public UserController(UserService userService,UserRepository userRepository){
        this.userService=userService;
        this.userRepository=userRepository;
    }
    @PostMapping
    public User createUser(@Valid @RequestBody UserRequest userRequests){

        User user= new User();
        user.setUsername(userRequests.getUsername());
        user.setEmail(userRequests.getEmail());
        user.setPassword(userRequests.getPassword());
        return userService.createUser(user);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserRequest userRequest) {

        return userService.login(
                userRequest.getEmail(),
                userRequest.getPassword()
        );
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


}

