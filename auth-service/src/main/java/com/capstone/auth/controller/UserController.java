package com.capstone.auth.controller;

import com.capstone.auth.entity.ResponseDto;
import com.capstone.auth.entity.ResponseMessage;
import com.capstone.auth.entity.UserEntity;
import com.capstone.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<ResponseDto> getAllUsers() {
        try {
            List<UserEntity> allUsers = userService.getAllUsers();
            return new ResponseEntity<>(new ResponseDto(200, "Successfully get All Users!", allUsers), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Data no Exist!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<ResponseDto> getUserById(@PathVariable("userId") int id) {
        try {
            UserEntity user = userService.getUserById(id);
            return new ResponseEntity<>(new ResponseDto(200, "Successfully get User!", user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Data no Exist!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<ResponseDto> addUser(@ModelAttribute UserEntity requestUser) {
        try {
            UserEntity addUser = userService.addUser(requestUser);
            return new ResponseEntity<>(new ResponseDto(200, "Successfully add User!", addUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Data no Exist!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<ResponseDto> updUser(@ModelAttribute UserEntity requestUser) {
        try {
            UserEntity updUser = userService.updateUser(requestUser);
            return new ResponseEntity<>(new ResponseDto(200, "Successfully update User!", updUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Data no Exist!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable("userId") int id) {
        try {
            ResponseMessage deleteUser = userService.deleteUser(id);
            return new ResponseEntity<>(new ResponseDto(200, "Successfully delete User!", deleteUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Data no Exist!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }
}
