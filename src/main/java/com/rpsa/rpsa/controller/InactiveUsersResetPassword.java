package com.rpsa.rpsa.controller;
import com.rpsa.rpsa.dto.ResetPasswordDTO;
import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.repository.T_userloginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/public")
public class InactiveUsersResetPassword {

    @Autowired
    private T_userloginRepository userLoginRepo;
    @GetMapping("/resetexpiredpassword")
    public String inactiveUsersResetPassword(){
        return "/pages/resetExpiredPassword/resetexpiredpassword";
    }

    @PostMapping("/savenewpassword")
    @ResponseBody
    public String expPassReset(@RequestBody ResetPasswordDTO resetPasswordDto){
        String res = "Initiated";
        T_userlogin user = userLoginRepo.findByUsername(resetPasswordDto.getUsername());
        if(user!= null){
            if (user.getUserpassword().equals(resetPasswordDto.getCurrentpassword())){
                user.setUserpassword(resetPasswordDto.getNewpassword());
                user.setActiveDays(0);
                userLoginRepo.save(user);
                res = "Password Changed Successfully. Please Log In wth your new password.";
            }else{
                res = "Current Password is incorrect, Please enter valid password.";
            }
        }else{
            res = "User Not Found";
        }
        return res;
    }

}
