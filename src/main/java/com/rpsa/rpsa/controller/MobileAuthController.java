package com.rpsa.rpsa.controller;


import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MobileAuthController {

    @Autowired
    private AuthService authService;

//    @PostMapping("/mregister")
//    public ResponseEntity<T_userlogin> registerMobileUser(@RequestBody T_userlogin user){
//        System.out.println("################Registered user details####################" + user);
//        T_userlogin newUser = authService.registerUser(user);
//        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//    }

    @GetMapping("/getallusers")
    public List<T_userlogin> getAllUsers() {
        return authService.getUserList();
    }
}
