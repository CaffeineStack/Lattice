package com.lattice.controller;
import com.lattice.dto.LoginResponse;
import com.lattice.dto.LoginRequest;
import com.lattice.model.User;
import com.lattice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        return userService.login(
                request.getEmail(),
                request.getPassword()
        );
    }



}

