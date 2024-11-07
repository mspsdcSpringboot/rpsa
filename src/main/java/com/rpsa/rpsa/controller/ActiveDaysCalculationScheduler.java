package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.repository.T_userloginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActiveDaysCalculationScheduler {

    @Autowired
    private T_userloginRepository userRepository;
    @Scheduled(cron = "0 0 12 * * *")
    @PostMapping("/updateActiveDays")
    public void updateActiveDays(){
        List<T_userlogin> users = userRepository.findAll();
        for (T_userlogin user : users) {
            int newActiveDays = user.getActiveDays() + 1;
            user.setActiveDays(newActiveDays);
            userRepository.save(user);
        }
    }
}
