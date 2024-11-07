package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.repository.Khadc_InitiatedDataRepository;
import com.rpsa.rpsa.repository.Khadc_PaymentsRepository;
import com.rpsa.rpsa.repository.M_processesRepository;
import com.rpsa.rpsa.repository.UserRoleRepository;
import com.rpsa.rpsa.service.ProcessService;
import com.rpsa.rpsa.service.T_UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/secure")

public class KhaDcController {

    @Autowired
    private T_UserService userService;

    @Autowired
    private M_processesRepository processRepo;

    @Autowired
    private ProcessService processService;

    @Autowired
    private UserRoleRepository userRoleRepo;

    @Autowired
    private Khadc_InitiatedDataRepository khadcRepo;
    @Autowired
    private Khadc_PaymentsRepository khadcPayRepo;

    @GetMapping("/khadcdashboard")
    public String khaDcDashboard(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        List<Khadc_InitiatedData> reg = khadcRepo.findAllByServicecode("1371");
        List<Khadc_InitiatedData> enr = khadcRepo.findAllByServicecode("1372");


        model.addAttribute("userData", user);
        model.addAttribute("regcount", reg.size());
        model.addAttribute("enrcount", enr.size());
        model.addAttribute("enrlist", enr);
        model.addAttribute("reglist", reg);


        return "pages/secure/khadc/khadcdashboard";

    }


    @GetMapping("/viewKhadcPaymentDetails")
    public String viewKhadcPaymentDetails(@RequestParam("applicationid") String applicationid, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        List<Khadc_Payments> payments = khadcPayRepo.findByApplicationid_Applicationid(applicationid);
        int totalAmount = 0;
        for (Khadc_Payments payment : payments) {
            int amount = Integer.parseInt(payment.getAmount());
            totalAmount += amount;
        }

        if (!payments.isEmpty()) {
            int lastIndex = payments.size() - 1;
            Khadc_Payments lastPayment = payments.get(lastIndex);
            model.addAttribute("lastPayment", lastPayment.getAmount() + " for " + lastPayment.getFyear());
        } else {
            model.addAttribute("lastPayment", "No Data Available");
        }

        model.addAttribute("userData", user);
        model.addAttribute("paymentslist", payments);
        model.addAttribute("totalAmount", totalAmount);



        return "pages/secure/khadc/viewapplicationdetails";

    }

    @GetMapping("/khadcestablishmentdashboard")
    public String khaDcEstablishmentDashboard(
            @RequestParam(defaultValue = "0") int page,  // 'page' parameter to handle pagination
            Model model) {

        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        List<Khadc_Payments> allData = khadcPayRepo.findAll();

        // Fetch paginated payments (20 per page)
        Page<Khadc_Payments> paymentPage = khadcPayRepo.findAll(PageRequest.of(page, 10));

        // Get the list of payments from the current page
        List<Khadc_Payments> paymentlist = paymentPage.getContent();

        // Calculate total amount for the current page
        int totalAmount = calculateTotalAmount(allData);

        // Add data to the model
        model.addAttribute("userData", user);
        model.addAttribute("paymentlist", paymentlist);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paymentPage.getTotalPages());

        return "pages/secure/khadc/khadcpaymentdashboard";
    }

    private int calculateTotalAmount(List<Khadc_Payments> paymentlist) {
        int totalAmount = 0;
        for (Khadc_Payments payment : paymentlist) {
            String amountStr = payment.getAmount();
            if (amountStr != null && !amountStr.isEmpty()) {
                try {
                    String cleanedAmountStr = amountStr.replaceAll("[^\\d]", "");  // Clean non-numeric characters
                    int amount = Integer.parseInt(cleanedAmountStr);
                    totalAmount += amount;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid amount format: " + amountStr);
                }
            }
        }
        return totalAmount;
    }


    @GetMapping("/khadcdatewisepaymentdashboard")
    public String khaDcDatewisePaymentDashboard(
            @RequestParam("datefrom") String dateFromStr,
            @RequestParam("dateto") String dateToStr,
            Model model) {

        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        System.out.println("...................Inside date wise......................");
        System.out.println("...................dateFrom......................"+dateFromStr);
        System.out.println("...................dateTo......................"+dateToStr);
        // Convert String to LocalDate
        LocalDate dateFrom = LocalDate.parse(dateFromStr);
        LocalDate dateTo = LocalDate.parse(dateToStr);

        // Convert LocalDate to Timestamp
        Timestamp startDate = Timestamp.valueOf(dateFrom.atStartOfDay());
        Timestamp endDate = Timestamp.valueOf(dateTo.atStartOfDay().plusDays(1));

        // Fetch data between the provided dates
        List<Khadc_Payments> filteredPayments = khadcPayRepo.findByPaymentdateBetween(startDate, endDate);

        // Calculate total amount for the filtered data
        int totalAmount = calculateTotalAmount(filteredPayments);

        // Add data to the model
        model.addAttribute("userData", user);
        model.addAttribute("paymentlist", filteredPayments);
        model.addAttribute("totalAmount", totalAmount);

        return "pages/secure/khadc/khadcdatewisepaymentdashboard";
    }



}
