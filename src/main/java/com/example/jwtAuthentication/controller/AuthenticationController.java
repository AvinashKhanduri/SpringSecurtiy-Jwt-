package com.example.jwtAuthentication.controller;


import com.example.jwtAuthentication.dto.request.AuthenticationRequest;
import com.example.jwtAuthentication.dto.request.RegisterRequest;
import com.example.jwtAuthentication.dto.response.AuthenticationResponse;
import com.example.jwtAuthentication.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth-service")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
            return authenticationService.register(registerRequest);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam("token") String refreshToken) {
            return authenticationService.refereshToken(refreshToken);

    }

    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
       return ResponseEntity.ok(authenticationService.validateToekn(token));
    }

}
