package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.dto.DoSubOrdinateDTO;
import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.repository.*;
import com.rpsa.rpsa.service.AuthService;
import com.rpsa.rpsa.service.ProcessService;
import com.rpsa.rpsa.service.T_UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/secure")
public class DoController {

    @Autowired
    private T_UserService userService;

    @Autowired
    private M_processesRepository processRepo;

    @Autowired
    private ProcessService processService;

    @Autowired
    private TAppealsRepository appealsRepo;

    @Autowired
    private T_DOAlertsRepository alertsRepo;

    @Autowired
    private T_userloginRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private AuthService authService;




    @GetMapping("/doinbox")
    public String vahanDashboard(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();

        List<T_Appeals> appealsUnderYou = appealsRepo.findAllByOfficeid(user.getOfficeid());
        List<T_Appeals> commissionerDirection = appealsUnderYou.stream()
                                                .filter(appeal -> appeal.getDoaction() == null)
                                                .filter(appeal -> "2".equals(appeal.getAppeallevel()))
                                                .filter(appeal -> appeal.getCommissionactioncode() != null)
                                                .filter(appeal -> "8".equals(appeal.getCommissionactioncode().getActioncode()))
                                                .filter(appeal -> "7".equals(appeal.getStatusid().getStatusid()))
                                                .collect(Collectors.toList());
        List<T_DOAlerts> doAlerts = alertsRepo.findAllByOfficeid(user.getOfficeid());



        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("appealslist", appealsUnderYou);
        model.addAttribute("directionslist", commissionerDirection);
        model.addAttribute("appealsdocount", appealsUnderYou.size());
        model.addAttribute("directionscount", commissionerDirection.size());
        model.addAttribute("alertslist", doAlerts);



        return "pages/secure/inbox/doinbox";
    }

    @GetMapping("/dosubordinate")
    public String doSubordinate(Model model) {

//        String page = "pages/secure/Subordinate/createdosubordinate";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();

        if (user.getOfficeid() != null){
            UserRole userRole = roleRepository.findByRoleid("10");
            List<T_userlogin> doSubOrdinate = userRepository.findByOfficeidAndUserrole(user.getOfficeid(), userRole);
            model.addAttribute("sOrdinateList", doSubOrdinate);
        }





        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);




        return "pages/secure/Subordinate/createdosubordinate";
    }

    @PostMapping("/createdosubordinate")
    @ResponseBody
    public String createDoSubordinate(@RequestBody DoSubOrdinateDTO doSubDto){
        return authService.createDoSub(doSubDto);
    }

    @PostMapping("/deactivedosubordinate")
    @ResponseBody
    public String deactiveDoSubordinate(@RequestParam("id") String id){
        T_userlogin user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found !"));
        if(user != null && Objects.equals(user.getActive(), "Y")){
            user.setActive("N");
            userRepository.save(user);
        }
        return "updated";
    }

    @PostMapping("/activedosubordinate")
    @ResponseBody
    public String activeDoSubordinate(@RequestParam("id") String id){
        T_userlogin user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found !"));
        if(user != null && Objects.equals(user.getActive(), "N")){
            user.setActive("Y");
            userRepository.save(user);
        }
        return "updated";
    }
    @PostMapping("/forwarddoappeal")
    @ResponseBody
    public String forwardDoAppeal(@RequestParam String appealcode, @RequestParam String usercode){
        String res = "iniitated";
        System.out.println("appealcode" + appealcode);
        System.out.println("usercode" + usercode);
        T_Appeals appeal = appealsRepo.findById(appealcode).orElseThrow(() -> new EntityNotFoundException("Appeal Not Found!"));
        if (appeal != null){
            T_userlogin user = userRepository.findById(usercode).orElseThrow(() -> new EntityNotFoundException("User Not Found!"));
            appeal.setDosubordinate(user);
            appealsRepo.save(appeal);
            res = "Appeal Forwarded Successfully";
        }else {
            res = "Unable to find the appeal";
        }
        return res;
    }

    @GetMapping("/dosubhome")
    public String doSubHome(Model model) {

//        String page = "pages/secure/Subordinate/createdosubordinate";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();

        List<T_Appeals> doSubAppeals = appealsRepo.findAllByDosubordinate(user);





        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("appealsdocount", doSubAppeals.size());
        model.addAttribute("appealslist", doSubAppeals);




        return "pages/secure/inbox/dosubhome";
    }
}
