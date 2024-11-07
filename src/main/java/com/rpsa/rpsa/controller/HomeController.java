package com.rpsa.rpsa.controller;
import com.rpsa.rpsa.dto.AppealToAppellateDTO;
import com.rpsa.rpsa.dto.ApplealsToCommissionerDTO;
import com.rpsa.rpsa.dto.GalleryPhotoDTO;
import com.rpsa.rpsa.dto.SubmissionDetailsDto;
import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.service.LoadHomeService;
import com.rpsa.rpsa.service.T_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {

    @Autowired
    private LoadHomeService loadHomeService;

    @Autowired
    private T_UserService userService;

//    @Autowired
//    private SubmissionDetailsDto submissionDetailsDto;
    @GetMapping("/")
    public String getHomePage(Model model, Visitors visitors){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if(username == "anonymousUser"){
            //Getting the total visitors data
            String totalVisitors = loadHomeService.getVisitorsCount(visitors);
            model.addAttribute("visitorsCount", totalVisitors);



            //Getting the total services registered
            Integer totalServices = loadHomeService.getTotalServices();
            model.addAttribute("totalServices", totalServices);



            //Getting the contact information
            List<Contacts> contacts = loadHomeService.getContacts();
            model.addAttribute("contacts", contacts);



            //Getting all the photos for home page
            List<GalleryPhotoDTO> homePageGallery = loadHomeService.getHomePageGallery();
            model.addAttribute("homePageGallery", homePageGallery);



            //Getting notice board data
            List<WhatsNew> noticeBoardData = loadHomeService.getNoticeBoardData();
            model.addAttribute("noticeboard", noticeBoardData);




            //Getting overall information of application submissions
            SubmissionDetailsDto submissionDTO = loadHomeService.getOverallInfo();
            model.addAttribute("submissionDetails", submissionDTO);



            //Getting information for Appeals To Appellate
            AppealToAppellateDTO appealsDetailsDTO = loadHomeService.getAppealToAppellateDetails();
            model.addAttribute("appeallateAppeals", appealsDetailsDTO);

            System.out.println("appeallateAppeals  "+ appealsDetailsDTO);
//
//
//            //Getting information for Appeals To Commissioner
            ApplealsToCommissionerDTO applealsToCommissionerDTO = loadHomeService.getAppealToCommissionerDetails();
            model.addAttribute("commissionerAppeals", applealsToCommissionerDTO);

            System.out.println("commissionerAppeals"+ applealsToCommissionerDTO);


            return "home_page";
//            return "pages/test";
        }


        else{
            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
            String username1 = authentication.getName();
            T_userlogin user = userService.findByUsername(username);

            if(Objects.equals(user.getUserrole().getRoleid(), "2")){
                return "redirect:/secure/aainbox";
            }
            if(Objects.equals(user.getUserrole().getRoleid(), "1")){
                return "redirect:/secure/mapprocess";
            }
            if(Objects.equals(user.getUserrole().getRoleid(), "8")){
                return "redirect:/secure/khadcdashboard";
            }if(Objects.equals(user.getUserrole().getRoleid(), "6")){
                return "redirect:/secure/aasoinbox";
            }

            return "redirect:/secure/home";
        }

    }
}
