package com.capstone.auth.controller;

import com.capstone.auth.entity.AuthenticationResponse;
import com.capstone.auth.entity.ResponseDTO;
import com.capstone.auth.entity.UserEntity;
import com.capstone.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserEntity user) {
        try {
            Integer isUserExist = authenticationService.isUserExist(user.getEmail());
            if(isUserExist == 0) {
                AuthenticationResponse token = authenticationService.register(user);
                ResponseDTO response = new ResponseDTO(200, "Successfully Register!", token);
                return ResponseEntity.ok(response);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserEntity user) {
        AuthenticationResponse token = authenticationService.authenticate(user);
        ResponseDTO response = new ResponseDTO(200, "Successfully Login!", token);
        return ResponseEntity.ok(response);
    }
}
