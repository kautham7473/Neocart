package com.ecommerce.user_service.controller;

import com.ecommerce.user_service.dto.LoginDto;
import com.ecommerce.user_service.entity.User;
import com.ecommerce.user_service.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sign")
@CrossOrigin(origins = {"http://localhost:5173", "https://neocart-ui.onrender.com"})
public class SigninController {
    private final SignInService signinService;

    @Autowired
    public SigninController(SignInService signinService) {
        this.signinService = signinService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            User user = signinService.authenticateUser(loginDto.getUsername(), loginDto.getPassword());
            return ResponseEntity.ok(Map.of(
                    "message", "Login Succesfull",
                    "user", user
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminAccess() {
        return ResponseEntity.ok("Welcome, Admin!");
    }

}
