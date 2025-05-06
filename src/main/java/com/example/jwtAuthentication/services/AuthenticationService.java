package com.example.jwtAuthentication.services;

import com.example.jwtAuthentication.dto.request.AuthenticationRequest;
import com.example.jwtAuthentication.dto.request.RegisterRequest;
import com.example.jwtAuthentication.dto.response.AuthenticationResponse;
import com.example.jwtAuthentication.entity.Role;
import com.example.jwtAuthentication.entity.User;
import com.example.jwtAuthentication.repository.UserRepsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import java.util.HashMap;
import java.util.List;

@Service
public  class AuthenticationService {

    @Autowired
    private UserRepsitory userRepsitory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtServices jwtServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> register(RegisterRequest registerRequest){
        try{
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setRole(List.of(Role.user));
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            userRepsitory.saveUser(user);

            return ResponseEntity.ok("User created successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("somethign went wrong"+e.getMessage());
        }


    }

    public  ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest){
       try{
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword())
           );

           User user = userRepsitory.findByEmail(authenticationRequest.getEmail());
           String jwtToken = jwtServices.generateToken(user);
           String refereshToken = jwtServices.generateRefresh(new HashMap<>(),user);
           AuthenticationResponse authenticationResponse = new AuthenticationResponse();
           authenticationResponse.setAuthenticationToken(jwtToken);
           authenticationResponse.setRefreshToken(refereshToken);
           return ResponseEntity.ok(authenticationResponse);
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }

    }


    public ResponseEntity<?> refereshToken(String refereshToken){
        try{
            User user = userRepsitory.findByEmail(jwtServices.getEmailFromToken(refereshToken));
            String jwtToken = jwtServices.generateToken(user);
            String newRefereshToken = jwtServices.generateRefresh(new HashMap<>(),user);
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setRefreshToken(newRefereshToken);
            authenticationResponse.setAuthenticationToken(jwtToken);
            return ResponseEntity.ok(authenticationResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    public Boolean validateToekn(String token){
        return jwtServices.validateToken(token);
    }


}