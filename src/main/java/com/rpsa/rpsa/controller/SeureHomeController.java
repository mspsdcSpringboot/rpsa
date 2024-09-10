package com.rpsa.rpsa.controller;
import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.service.T_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/secure")

public class SeureHomeController {

    @Autowired
    private T_UserService userService;



    @GetMapping("/home")
    public String secureHome(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        model.addAttribute("userData", user);

        return "pages/secure/home";
    }

}
