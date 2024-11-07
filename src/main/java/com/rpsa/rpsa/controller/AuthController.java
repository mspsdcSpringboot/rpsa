package com.rpsa.rpsa.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.rpsa.rpsa.dto.LoginUserDto;
import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.repository.T_userloginRepository;
import com.rpsa.rpsa.service.AuthService;
import com.rpsa.rpsa.service.CustomUserDetailsServiceImpl;
import com.rpsa.rpsa.service.ProcessService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private T_UserService userDataService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private T_userloginRepository userRepo;

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
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDto credentials, HttpServletResponse response) {
        System.out.println("##################Login Credentials###################" + credentials);
        T_userlogin userFind = null;
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(credentials.getUsername());

            System.out.println("User Details - " + userDetails);

            T_userlogin activeUser = userDataService.findByUsername(userDetails.getUsername());

            boolean isActive = activeUser.getActiveDays() <= activeUser.getUserActiveDuration().getActiveDuration();

            if (isActive) {
                String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

                Cookie authCookie = new Cookie("authToken", jwtToken);
                authCookie.setHttpOnly(true);
                authCookie.setSecure(false); // Use true if your app is HTTPS
                authCookie.setPath("/");

                response.addCookie(authCookie);

                userFind = userDataService.findByUsername(userDetails.getUsername());

                return new ResponseEntity<>(userFind, HttpStatus.OK);
            }else {
                return new ResponseEntity<>("User is inactive. Please reset password", HttpStatus.CONFLICT);
            }




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

    @GetMapping("/secure/changeprofile")
    public String changeProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userDataService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());



        model.addAttribute("userData", user);
        model.addAttribute("processes", process);



        return "/pages/secure/profile/changeprofile";
    }

    @PostMapping("/secure/updateprofile")
    @ResponseBody
    public String updateProfile(@RequestBody JsonNode userNode, HttpServletRequest request, HttpServletResponse response){
        String res = "initialized";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userDataService.findByUsername(username);

        String oldUsername =  user.getUsername();

        user.setFullname(userNode.get("fullname").asText());
        user.setContact(userNode.get("contact").asText());
        user.setEmail(userNode.get("email").asText());
        user.setUsername(userNode.get("username").asText());
        user.setDesignation(userNode.get("designation").asText());
        userRepo.save(user);

        if(!Objects.equals(oldUsername, user.getUsername())){
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
            res = "username";
        }else if(user.getFullname().equals(userNode.get("fullname").asText())){
            res = "updated";
        }
        else{
            res = "failed";
        }
        return res;
    }


    @GetMapping("/secure/changepassword")
    public String changePassword(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userDataService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());



        model.addAttribute("userData", user);
        model.addAttribute("processes", process);



        return "/pages/secure/profile/changepassword";
    }

    @PostMapping("/secure/updatepassword")
    @ResponseBody
    public String updatePassword(@RequestBody JsonNode passwordNode){
        String res = "iniitalized";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userDataService.findByUsername(username);

        if(!Objects.equals(user.getUserpassword(), passwordNode.get("currentpassword").asText())){
            res = "Incorrect Password! Please try again";
        }else{
            user.setUserpassword(passwordNode.get("newpassword").asText());
            userRepo.save(user);
            res = "Password Updated Successfully!";
        }

        return res;
    }






}
