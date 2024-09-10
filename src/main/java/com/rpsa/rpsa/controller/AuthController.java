package com.rpsa.rpsa.controller;


import com.rpsa.rpsa.dto.LoginUserDto;
import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.service.AuthService;
import com.rpsa.rpsa.service.CustomUserDetailsServiceImpl;
import com.rpsa.rpsa.service.T_UserService;
import com.rpsa.rpsa.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private T_UserService userDataService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String loginPage() {
        return "/pages/auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new T_userlogin());
        return "/pages/auth/registration";
    }

    @PostMapping("/register")
    @ResponseBody
    public String registerNewUser(@RequestBody T_userlogin registerUser) {
        System.out.println("################Registered user details####################" + registerUser);
        return authService.registerUser(registerUser);
    }


    @PostMapping("/userlogin")
    @ResponseBody
    public ResponseEntity<T_userlogin> loginUser(@RequestBody LoginUserDto credentials, HttpServletResponse response) {
        System.out.println("##################Login Credentials###################" + credentials);
        T_userlogin userFind = null;
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(credentials.getUsername());

            System.out.println("User Details - " + userDetails);


            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

            Cookie authCookie = new Cookie("authToken", jwtToken);
            authCookie.setHttpOnly(true);
            authCookie.setSecure(false); // Use true if your app is HTTPS
            authCookie.setPath("/");

            response.addCookie(authCookie);

            userFind = userDataService.findByUsername(userDetails.getUsername());

            return new ResponseEntity<>(userFind, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(userFind, HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/secure/userlogout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Invalidate the session to remove the JSESSIONID
            request.getSession().invalidate();

            // Remove the JSESSIONID cookie by setting its max age to 0
            Cookie jsessionidCookie = new Cookie("JSESSIONID", null);
            jsessionidCookie.setPath("/");
            jsessionidCookie.setMaxAge(0);
            response.addCookie(jsessionidCookie);

            // Remove the authToken cookie as well
            Cookie authCookie = new Cookie("authToken", null);
            authCookie.setHttpOnly(true);
            authCookie.setSecure(false); // Use true if your app is HTTPS
            authCookie.setPath("/");
            authCookie.setMaxAge(0);
            response.addCookie(authCookie);

            return new ResponseEntity<>("Logout successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Logout failed", HttpStatus.BAD_REQUEST);
        }
    }






}
