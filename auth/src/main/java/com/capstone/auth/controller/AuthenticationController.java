package com.capstone.auth.controller;

import com.capstone.auth.entity.AuthenticationResponse;
import com.capstone.auth.entity.UserEntity;
import com.capstone.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserEntity user) {
        try {
            Integer isUserExist = authenticationService.isUserExist(user.getEmail());
            if(isUserExist == 0) {
                return ResponseEntity.ok(authenticationService.register(user));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserEntity user) {
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }
}
