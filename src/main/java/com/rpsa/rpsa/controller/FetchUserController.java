package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.dto.PushUserDto;
import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.service.T_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")

//@RequestMapping("/secure")
public class FetchUserController {

    @Autowired
    private T_UserService userService;


    @GetMapping("/fetch-user")
    public ResponseEntity<Object> fetchUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        T_userlogin user = userService.findByUsername(username);


        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
        }
    }
}
