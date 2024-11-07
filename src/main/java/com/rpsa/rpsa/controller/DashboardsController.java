package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.repository.*;
import com.rpsa.rpsa.service.ProcessService;
import com.rpsa.rpsa.service.T_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/secure")
public class DashboardsController {

    @Autowired
    private T_UserService userService;

    @Autowired
    private M_processesRepository processRepo;

    @Autowired
    private ProcessService processService;

    @Autowired
    private UserRoleRepository userRoleRepo;

    @Autowired
    private TransportDataDumpRepository transportRepo;

    @Autowired
    private VahanTotalDashboardRepository vahanTotalDashboardRepo;

    @Autowired
    private VahanServiceWiseDashboardRepository vahanServiceWiseDashboardRepo;
    @GetMapping("/transportdashboard")
    public String transportDashboard(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();

        String submissionlocationid = null;
        if (user.getDistrictcode().getDistrictcode().equals("276")) {
            submissionlocationid = "ML10";
        } else if (user.getDistrictcode().getDistrictcode().equals("274")) {
            submissionlocationid = "ML05";
        } else if (user.getDistrictcode().getDistrictcode().equals("275")) {
            submissionlocationid = "ML04";
        } else if (user.getDistrictcode().getDistrictcode().equals("740")) {
            submissionlocationid = "ML15";
        } else if (user.getDistrictcode().getDistrictcode().equals("273")) {
            submissionlocationid = "ML07";
        } else if (user.getDistrictcode().getDistrictcode().equals("657")) {
            submissionlocationid = "ML11";
        } else if (user.getDistrictcode().getDistrictcode().equals("656")) {
            submissionlocationid = "ML13";
        } else if (user.getDistrictcode().getDistrictcode().equals("277")) {
            submissionlocationid = "ML09";
        } else if (user.getDistrictcode().getDistrictcode().equals("663")) {
            submissionlocationid = "ML14";
        } else if (user.getDistrictcode().getDistrictcode().equals("658")) {
            submissionlocationid = "ML12";
        } else if (user.getDistrictcode().getDistrictcode().equals("278")) {
            submissionlocationid = "ML08";
        } else if (user.getDistrictcode().getDistrictcode().equals("279")) {
            submissionlocationid = "ML06";
        }

        List<Object[]> repostList = transportRepo.getServiceWiseSummary(submissionlocationid);
        List<Object[]> result = transportRepo.getSummedDataBySubmissionLocation(submissionlocationid);

//        if (result == null || result.isEmpty()) {
//            return new int[]{0, 0, 0, 0}; // return an empty array if no data found
//        }

        // Get the first result and map to long[]
        Object[] resultArray = result.get(0);

        long[] summedData = new long[resultArray.length];
        for (int i = 0; i < resultArray.length; i++) {
            summedData[i] = resultArray[i] != null ? ((Number) resultArray[i]).longValue() : 0;
        }


        System.out.println("counts  " + summedData);



        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("reportlist", repostList);
        model.addAttribute("jdata", summedData);


        return "pages/secure/dashboards/transposrtdashboard";
    }


    @GetMapping("/vahandashboard")
    public String vahanDashboard(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        VahanTotalDashboard totalDashboard = vahanTotalDashboardRepo.findById(1L).orElse(null);
        List<VahanServiceWiseDashboard> serviceWiseDashboard = vahanServiceWiseDashboardRepo.findAll();


        List<M_Processes> processList = processRepo.findAll();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("totalDashboard", totalDashboard);
        model.addAttribute("serviceWiseDashboard", serviceWiseDashboard);


        return "pages/secure/dashboards/vahandashboard";
    }

    @GetMapping("/officewiselisvahan/{purCd}")
    public String vahanDashboardList(@PathVariable String purCd, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        List<M_Processes> processList = processRepo.findAll();


        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);


        return "pages/secure/dashboards/officewisevahanlist";
    }

}
