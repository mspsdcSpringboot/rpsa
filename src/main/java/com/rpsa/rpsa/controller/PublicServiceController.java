package com.rpsa.rpsa.controller;
import com.rpsa.rpsa.model.M_notifiedservicescount;
import com.rpsa.rpsa.model.OnlineServices;
import com.rpsa.rpsa.repository.M_notifiedservicescountRepository;
import com.rpsa.rpsa.repository.OnlineServicesRepository;
import com.rpsa.rpsa.service.T_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/public")
public class PublicServiceController {

    @Autowired
    private OnlineServicesRepository serviceRepo;

    @Autowired
    private M_notifiedservicescountRepository serviceCountRepo;

    @GetMapping("/onlineservices")
    public String getOnlineServicePage(Model model) {

        System.out.println("------------------------------------------------------onlineservices");


//        List<OnlineServices> allServices = serviceRepo.findAll();
        List<OnlineServices> offlineServices = serviceRepo.findByOnlineIn(Arrays.asList("0", "-1"));
        List<OnlineServices> allServices = serviceRepo.findByOnlineIn(Arrays.asList("1", "2"));
        M_notifiedservicescount notifiedServiceCount = serviceCountRepo.findById("1").orElse(null);


//        model.addAttribute("serviceslist", onlineServices);
        model.addAttribute("offlineserviceslist", offlineServices);
        model.addAttribute("serviceslist", allServices);


        model.addAttribute("scount", notifiedServiceCount.getScount());
        model.addAttribute("serviceslistsize", allServices.size());
        model.addAttribute("offlineserviceslistsize", offlineServices.size());

//        System.out.println("All Services COunt **************************" + allServices.size());
        System.out.println(allServices.size());
        System.out.println(offlineServices.size());




        return "pages/publicService/onlineservices";
    }


    @GetMapping("/applybtnclick")
    @ResponseBody
    public String applybtnclick(String slno, Model model) {
        serviceRepo.incrementApplyClick(slno);
        return "1";

    }

}
