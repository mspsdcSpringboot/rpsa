package com.rpsa.rpsa.helper;
import cn.apiclub.captcha.Captcha;
import com.rpsa.rpsa.model.T_userlogin;

public class CaptchaHelper {

    public static void getCaptcha(T_userlogin userlogin) {
        try {
            Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
            userlogin.setHiddenCaptcha(captcha.getAnswer());
            userlogin.setCaptcha(""); // value entered by the User
            userlogin.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
        } catch (Exception e) {
            throw new RuntimeException("Captcha cannot be generated!");
        }
    }
}
