package com.example.jwtAuthentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

        @PostMapping("/testAdmin")
        public ResponseEntity<String> seyHello() {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.ok("Hello from Admin Controller! "+userName);
        }
}
