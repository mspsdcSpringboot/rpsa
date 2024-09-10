package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.helper.CaptchaHelper;
import com.rpsa.rpsa.model.T_userlogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CaptchaController {

    @ResponseBody
    @GetMapping("/refresh-captcha")
    public T_userlogin refreshCaptcha() {
        T_userlogin users = new T_userlogin();
        CaptchaHelper.getCaptcha(users);
        return users;
    }
}
