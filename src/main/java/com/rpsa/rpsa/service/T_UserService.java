package com.rpsa.rpsa.service;

import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.repository.T_userloginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class T_UserService {

    @Autowired
    private T_userloginRepository userRepository;
    public T_userlogin findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
