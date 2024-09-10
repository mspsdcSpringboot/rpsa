package com.rpsa.rpsa.service;
import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.repository.T_userloginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private T_userloginRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        T_userlogin user = userRepository.findByUsername(userName);
        if(user != null){
            UserDetails userDetails = User.builder()
                    .username(user.getUsername())
                    .password(user.getUserpassword())
                    .build();
            return userDetails;
        }
        else {
            throw new UsernameNotFoundException("User not found with username: " + userName);
        }
    }
}
