package com.rpsa.rpsa.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpsa.rpsa.dto.AppealProcessDTO;
import com.rpsa.rpsa.dto.AppealProcessResponseDTO;
import com.rpsa.rpsa.dto.DoSubOrdinateDTO;
import com.rpsa.rpsa.dto.ForwardAppealDTO;
import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.repository.*;
import com.rpsa.rpsa.service.AuthService;
import com.rpsa.rpsa.service.ProcessService;
import com.rpsa.rpsa.service.T_UserService;
import com.rpsa.rpsa.utils.BharatVC;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/secure")

public class AppellateAuthorityController {

    @Autowired
    private T_UserService userService;

    @Autowired
    private M_processesRepository processRepo;

    @Autowired
    private ProcessService processService;

//    @Autowired
//    private TAppealsRepository appealsRepo;

    @Autowired
    private T_DOAlertsRepository alertsRepo;

    @Autowired
    private T_userloginRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private M_AppelateRepository m_AppelateRepository;

    @Autowired
    private TAppealsRepository appealsRepository;
    @Autowired
    private M_SubservicesRepository m_SubservicesRepository;

    @Autowired
    private M_ActionRepository actionRepo;

    @Autowired
    private M_RemarksRepository remarkRepo;

    @Autowired
    private T_TransactionssRepository transactionRepo;

    @Autowired
    private Audit_TrailRepository auditRepo;


    @GetMapping("/aasubordinate")
    public String aaSubordinate(Model model) {

//        String page = "pages/secure/Subordinate/createdosubordinate";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();

        if (user.getAppelateid() != null){
            UserRole userRole = roleRepository.findByRoleid("6");
            M_Appelate appelate = m_AppelateRepository.findById(user.getAppelateid().getAppelateid()).orElseThrow(() -> new EntityNotFoundException("Not found !"));
            List<T_userlogin> aaSubOrdinate = userRepository.findByAppelateidAndUserrole(appelate, userRole);
            model.addAttribute("sOrdinateList", aaSubOrdinate);
        }





        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);




        return "pages/secure/Subordinate/createaasubordinate";
    }

    @PostMapping("/createaasubordinate")
    @ResponseBody
    public String createAaSubordinate(@RequestBody DoSubOrdinateDTO doSubDto){
        return authService.createAaSub(doSubDto);
    }

    @PostMapping("/deactiveaasubordinate")
    @ResponseBody
    public String deactiveAaSubordinate(@RequestParam("id") String id){
        T_userlogin user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found !"));
        if(user != null && Objects.equals(user.getActive(), "Y")){
            user.setActive("N");
            userRepository.save(user);
        }
        return "updated";
    }

    @PostMapping("/activeaasubordinate")
    @ResponseBody
    public String activeAaSubordinate(@RequestParam("id") String id){
        T_userlogin user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found !"));
        if(user != null && Objects.equals(user.getActive(), "N")){
            user.setActive("Y");
            userRepository.save(user);
        }
        return "updated";
    }

    // Inbox for the APpelllate Authority SUb Ordinate
    @GetMapping("/aasoinbox")
    public String aaSubOrdinateInbox(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);



        String forwardusercode = user.getUsercode();


        List<T_Appeals> a = appealsRepository.getAllFAppeals(forwardusercode);
        model.addAttribute("appeallist", a);



        List<T_Appeals> f = appealsRepository.getAllFprocessedAppeals(forwardusercode);
        model.addAttribute("fappeallist", f);



        List<T_Appeals> pen = appealsRepository.getfpending(forwardusercode);
        model.addAttribute("pendingappeals", pen);



        List<T_Appeals> dis = appealsRepository.getfdisposed(forwardusercode);
        model.addAttribute("disposedappeallist", dis);
        model.addAttribute("disposedappeals", dis.size());



//        List<T_Appeals> dc = appealsRepository.getForwardDirection(forwardusercode);
//        model.addAttribute("directionslist", dc);


        model.addAttribute("appeallistcount", a.size() + f.size());
        model.addAttribute("processedappeallistcount", pen.size());
        model.addAttribute("newappeals", a.size());







        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());


        model.addAttribute("processes", process);
        model.addAttribute("userData", user);

        return "/pages/secure/inbox/aasoinbox";
    }


    @GetMapping("/aasoview/{appealcode}")
    public String getAaSoView(@PathVariable String appealcode, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        T_Appeals appeals = appealsRepository.findById(appealcode).orElse(null);

//        M_Designatedoffices dos = doRepo.findById(appeals.getOfficeid().getOfficeid()).orElse(null);

        List<M_Action> action = actionRepo.findAllByFlagAndActioncode();

        List<M_Remarks> remarks = remarkRepo.findAll();

        List<T_Transactionss> transactions = transactionRepo.findByAppealCodeOrderedByTransactionCodeDesc(appealcode);

//        List<T_userlogin> activeUser = usersRepo.findActiveUsersByAppelateId(user.getAppelateid().getAppelateid());


        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        model.addAttribute("processes", process);

        System.out.println("ACTIONS ARE -" + action);




        model.addAttribute("userData", user);
        model.addAttribute("appeallist", appeals);
        model.addAttribute("fprocessappeal", appeals);
        model.addAttribute("appealcode", appealcode);
        model.addAttribute("maction", action);
        model.addAttribute("remarkslist", remarks);
        model.addAttribute("transactionlist", transactions);
        model.addAttribute("remarkslist", remarks);

        return "/pages/secure/views/aasoview";
    }

    @PostMapping("/fProcessAppeal/{appealCode}")
    @ResponseBody
    public AppealProcessResponseDTO processAppeal(@ModelAttribute ForwardAppealDTO appealProcessDTO,
                                                  @RequestParam(value = "file1", required = false) MultipartFile file1,
                                                  @PathVariable String appealCode, HttpServletRequest request, HttpSession session) throws ParseException, IOException {

        //Received data format - AppealProcessDTO(appelateactioncode=3, remarks=1, remarkstxt=Service provided during pendency of Appeal. Hence treated as disposed, firsthearingdate=, hearingtype=1, hearingtime1=, hearingendtime1=, venue1=)

        System.out.println("appealProcessDTO" + appealProcessDTO);

        String res = "-1";

        AppealProcessResponseDTO responseDto = new AppealProcessResponseDTO();


        try{

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            T_userlogin user = userService.findByUsername(username);

            T_Appeals appeal = appealsRepository.findById(appealCode).orElse(null);

            if(appeal != null){

                appeal.setForwarddate(new Date());

                M_Status s = new M_Status();

                if (appealProcessDTO.getForwardactioncode().equals("1")) {
                    s.setStatusid("2");
                    if (appeal.getAppeallevel().equals("2")) {
                        appeal.setDoaction("1");
                    }
                } else if (appealProcessDTO.getForwardactioncode().equals("2")) {
                    s.setStatusid("3");
                    if (appeal.getAppeallevel().equals("2")) {
                        appeal.setDoaction("1");
                    }
                } else if (appealProcessDTO.getForwardactioncode().equals("3")) {
                    s.setStatusid("5");
                }
                appeal.setStatusid(s);
                Date hearingDate = convertStringToDate(appealProcessDTO.getFirsthearingdate());
                appeal.setFirsthearingdate(hearingDate);



                M_Action action = new M_Action();
                action.setActioncode(appealProcessDTO.getForwardactioncode());
                appeal.setForwardactioncode(action);
                appeal.setHearingtime1(appealProcessDTO.getHearingtime1());
                appeal.setForwardusercode(user);
                appeal.setLastactiondate(new Date());
                action = actionRepo.findById(appealProcessDTO.getForwardactioncode()).orElse(null);

                if(appealProcessDTO.getForwardactioncode().equals("1")){
                    if(appealProcessDTO.getHearingtype().equals("2")){

                        BharatVC vc = new BharatVC();
                        String title = "Hearing for Appeal No: " + appeal.getRefno();
                        String desc = "Hearing for Appeal No: " + appeal.getRefno();
                        String hearingdate1 = appealProcessDTO.getFirsthearingdate();
                        String startTime = appealProcessDTO.getHearingtime1();
                        String endTime = appealProcessDTO.getHearingendtime1();
                        String vcdetails = vc.sendVCReq(title, desc, hearingdate1, startTime, endTime);
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode appdata = objectMapper.readTree(vcdetails);
                        String appda = appdata.get("meetings").toString();
                        appda = appda.replace("[", "").replace("]", "");
                        JsonNode appdata2 = objectMapper.readTree(appda);

                        responseDto.setProcessType("hearingSuccess");
                        responseDto.setTitle(appdata2.get("title").asText().replaceAll("\"", ""));
                        responseDto.setVcLink("https://bharatvc.nic.in/join/" + appdata2.get("meetingID").asText().replaceAll("\"", ""));
                        responseDto.setDescription(appdata2.get("description").asText().replaceAll("\"", ""));
                        responseDto.setStartTime(appealProcessDTO.getHearingtime1());
                        responseDto.setEndTime(appealProcessDTO.getHearingendtime1());
                        responseDto.setPassword(appdata2.get("meetingPassword").asText().replaceAll("\"", ""));
                        responseDto.setHearingDate(hearingdate1);
                        responseDto.setUserData(user);

                        appeal.setHearingpw1(appdata2.get("meetingPassword").asText().replaceAll("\"", ""));
                        appeal.setLink1("https://bharatvc.nic.in/join/" + appdata2.get("meetingID").asText().replaceAll("\"", ""));

                    }
                    else {
                        appeal.setVenue1(appealProcessDTO.getVenue1());

                        responseDto.setProcessType("successPhysical");
                        responseDto.setAppealRefNo(appeal.getRefno());
                        responseDto.setUserData(user);

                    }
                    String firstHearingDateString = appealProcessDTO.getFirsthearingdate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    appeal.setFirsthearingdate(dateFormat.parse(firstHearingDateString));

                    appeal.setHearingtime1(appealProcessDTO.getHearingtime1());
                    appeal.setHearingendtime1(appealProcessDTO.getHearingendtime1());

                }
                else if(appealProcessDTO.getForwardactioncode().equals("2")){
                    Audit_Trail at = new Audit_Trail();
                    at.setIpaddress(request.getRemoteAddr());
                    at.setUrl(request.getRequestURL().toString());
                    at.setActions("Rejected Appeal No: " + appeal.getRefno());
                    at.setDatatime(new Date());
                    at.setUsercode(user);
                    Integer auditMaxId = auditRepo.findMaxId();

                    if (auditMaxId == null) {
                        auditMaxId = 0; // Starting id if no records exist
                    }

                    int newAuditMaxId = auditMaxId + 1;
                    String stringMaxId = String.valueOf(newAuditMaxId);
                    at.setAuditid(stringMaxId);

                    auditRepo.save(at);

                    responseDto.setProcessType("successReject");
                    responseDto.setAppealRefNo(appeal.getRefno());
                    responseDto.setUserData(user);
                }
                else if(appealProcessDTO.getForwardactioncode().equals("3")){
                    Audit_Trail at = new Audit_Trail();
                    at.setIpaddress(request.getRemoteAddr());
                    at.setUrl(request.getRequestURL().toString());
                    at.setActions("Disposed Appeal No: " + appeal.getRefno());
                    at.setDatatime(new Date());
                    at.setUsercode(user);
                    Integer auditMaxId = auditRepo.findMaxId();

                    if (auditMaxId == null) {
                        auditMaxId = 0; // Starting id if no records exist
                    }

                    int newAuditMaxId = auditMaxId + 1;
                    String stringMaxId = String.valueOf(newAuditMaxId);
                    at.setAuditid(stringMaxId);

                    auditRepo.save(at);

                    responseDto.setProcessType("successDispose");
                    responseDto.setAppealRefNo(appeal.getRefno());
                    responseDto.setUserData(user);
                }
                appeal.setAppelatedate(new Date());

//                M_Status status = new M_Status();
//
//                if (appealProcessDTO.getAppelateactioncode().equals("1")) {
//                    status.setStatusid("2");
//                } else if (appealProcessDTO.getAppelateactioncode().equals("2")) {
//                    status.setStatusid("3");
//                    if (appeal.getAppeallevel().equals("2")) {
//                        appeal.setDoaction("1");
//                    }
//                } else if (appealProcessDTO.getAppelateactioncode().equals("3")) {
//                    status.setStatusid("5");
//                    if (appeal.getAppeallevel().equals("2")) {
//                        appeal.setDoaction("1");
//                    }
//                }
//                appeal.setStatusid(status);



                T_Transactionss t = new T_Transactionss();
                if (file1.getBytes().length > 0) {
                    t.setOrderdoc(file1.getBytes());
                }
                t.setRemarks(appealProcessDTO.getRemarkstxt());

                if(appealProcessDTO.getForwardactioncode().equals("1")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    t.setHearingdate(dateFormat.parse(appealProcessDTO.getFirsthearingdate()));
                }

                t.setHearingtime(appealProcessDTO.getHearingtime1());
                t.setVclink(appeal.getLink1());
                t.setAppealcode(appeal);
                t.setAppeallevel(appeal.getAppeallevel());
                t.setActioncode(action);
                t.setTransactiondate(new Date());
                t.setUsercode(user);
                t.setTransactiondetails(action.getStatus());
                t.setHearingendtime(appealProcessDTO.getHearingendtime1());
                if (appealProcessDTO.getForwardactioncode().equals("1") && appealProcessDTO.getHearingtype().equals("2")) {

                    t.setHearingpw(appeal.getHearingpw1());
                } else if (appealProcessDTO.getForwardactioncode().equals("1") && appealProcessDTO.getHearingtype().equals("1")) {
                    t.setVenue(appealProcessDTO.getVenue1());
                }
                Integer transMaxId = transactionRepo.findMaxUnique();
                if (transMaxId == null) {
                    transMaxId = 0; // Starting id if no records exist
                }

                int newTransMaxId = transMaxId + 1;
                String stringTransMaxId = String.valueOf(newTransMaxId);


                t.setTransactionscode(stringTransMaxId);
                transactionRepo.save(t);


                if (appealProcessDTO.getForwardactioncode().equals("1")) {
                    if (appealProcessDTO.getHearingtype().equals("2")) {
                        if (!res.equals("-1")) {
                            Audit_Trail at = new Audit_Trail();
                            at.setIpaddress(request.getRemoteAddr());
                            at.setUrl(request.getRequestURL().toString());
                            at.setActions("Called for Online Hearing, Appeal No: " + appeal.getRefno());
                            at.setDatatime(new Date());
                            at.setUsercode(user);
                            auditRepo.save(at);

                            //TODO: NEED TO IMPLEMENT THE SMS SERVICE AS WELL AS THE EMAIL SERVICE HERE. NOW COMMENT OUT THE CODES. NEED TO TO IMPLEMENT IT LATER
//                            Sms sms = new Sms();
//                            String templateid = "1407165737038877013";
//                            String message = "Dear citizen, an online hearing is scheduled against your appeal " + ap.getRefno() + " on " + report.getFirsthearingdate() + " at " + report.getHearingtime1() + ".To join click " + ap.getLink1() + " with password " + ap.getHearingpw1() + ". You can also click on the link provided in your inbox.";
//                            String mnumber = ap.getUsercode().getContact();
//                            //System.out.println("s.sendSMSs(message, mnumber, templateid) " + sms.sendSMSs(message, mnumber, templateid));
//
//                            EmailServices em = new EmailServices();
//                            if (ap.getUsercode().getEmail() != null && !ap.getUsercode().getEmail().equals("")) {
//                                //System.out.println("EmailServices " + em.sendEmailAPI(ap.getUsercode().getEmail(), "Called for Hearing", message));
//
//                            }

                        }
                    } else //System.out.println("off");
                    {
                        if (!res.equals("-1")) {
                            Audit_Trail at = new Audit_Trail();
                            at.setIpaddress(request.getRemoteAddr());
                            at.setUrl(request.getRequestURL().toString());
                            at.setActions("Called for Offline Hearing, Appeal No: " + appeal.getRefno());
                            at.setDatatime(new Date());
                            at.setUsercode(user);
                            auditRepo.save(at);

                            //TODO: NEED TO IMPLEMENT THE SMS SERVICE AS WELL AS THE EMAIL SERVICE HERE. NOW COMMENT OUT THE CODES. NEED TO TO IMPLEMENT IT LATER

//                            Sms sms = new Sms();
//                            String templateid = "1407170020467438507";
//                            String message = "Dear citizen, an offline hearing is called against your appeal no. " + ap.getRefno() + " on " + report.getFirsthearingdate() + " " + report.getHearingtime1() + " at " + report.getVenue1() + ". For more details login and track your appeal status";
//                            String mnumber = ap.getUsercode().getContact();
//                            sms.sendSMSs(message, mnumber, templateid);
//
//                            EmailServices em = new EmailServices();
//                            if (ap.getUsercode().getEmail() != null && !ap.getUsercode().getEmail().equals("")) {
//                                em.sendEmailAPI(ap.getUsercode().getEmail(), "Called for Hearing", message);
//
//                            }

                        }
                    }
                }
                else if (appealProcessDTO.getForwardactioncode().equals("3") || appealProcessDTO.getForwardactioncode().equals("2")) {
                    if (!res.equals("-1")) {


                        //TODO: NEED TO IMPLEMENT THE SMS SERVICE AS WELL AS THE EMAIL SERVICE HERE. NOW COMMENT OUT THE CODES. NEED TO TO IMPLEMENT IT LATER

//                        Sms sms = new Sms();
//                        String templateid = "1407170021671065236";
//                        String message = "Dear citizen, your appeal " + ap.getRefno() + " has been disposed of. Kindly login and check the order";
//                        String mnumber = ap.getUsercode().getContact();
//                        sms.sendSMSs(message, mnumber, templateid);
//                        message = message + " \n Remarks: " + remarks;
//                        EmailServices em = new EmailServices();
//                        if (ap.getUsercode().getEmail() != null && !ap.getUsercode().getEmail().equals("")) {
//                            em.sendEmailAPI(ap.getUsercode().getEmail(), "Appeal Disposed", message);
//
//                        }

                    }
                }

            }

            else {
                res = "Appeal not found !";
            }


        }
        catch (Exception e) {
            res = "Exception - " + e.getMessage();
        }

        session.setAttribute("appealProcessData", responseDto);
        return responseDto;
    }

    @PostMapping("/recallAppeal")
    @ResponseBody
    public String recallAppeal(@RequestParam String appealcode, HttpServletRequest request){
        String res = "initiated";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        T_Appeals appeal =  appealsRepository.findById(appealcode).orElseThrow(() -> new EntityNotFoundException("Appeal Not Found!"));
        M_Status s = new M_Status();
        s.setStatusid("0");
        appeal.setStatusid(s);

        M_Action ac = new M_Action();
        ac.setActioncode("5");
        appeal.setAppelateactioncode(ac);

        appeal.setAppelatedate(new Date());
        appeal.setLastactiondate(new Date());
        String fusercode = appeal.getForwardusercode().getFullname();
        appeal.setForwardusercode(null);
        appealsRepository.save(appeal);

        T_Transactionss t = new T_Transactionss();
        t.setAppealcode(appeal);
        t.setAppeallevel(appeal.getAppeallevel());
        t.setActioncode(ac);
        t.setTransactiondate(new Date());
        t.setUsercode(user);
        t.setTransactiondetails("Recalled Appeal from " + fusercode);
        Integer transMaxId = transactionRepo.findMaxUnique();
        if (transMaxId == null) {
            transMaxId = 0; // Starting id if no records exist
        }

        int newTransMaxId = transMaxId + 1;
        String stringTransMaxId = String.valueOf(newTransMaxId);


        t.setTransactionscode(stringTransMaxId);
        transactionRepo.save(t);

        if (!res.equals("-1")) {
            Audit_Trail at = new Audit_Trail();
            Integer auditMaxId = auditRepo.findMaxId();

            if (auditMaxId == null) {
                auditMaxId = 0; // Starting id if no records exist
            }

            int newAuditMaxId = auditMaxId + 1;
            String stringMaxId = String.valueOf(newAuditMaxId);
            at.setAuditid(stringMaxId);
            at.setIpaddress(request.getRemoteAddr());
            at.setUrl(request.getRequestURL().toString());
            at.setActions("Recalled Appeal No:" + appealcode + " from " + fusercode);
            at.setDatatime(new Date());
            at.setUsercode(user);
            auditRepo.save(at);
//            Sms sms = new Sms();
//            String templateid = "1407165164689355591";
//            String message = "Dear Official, an appeal " + appeal.getAppealcode() + " under MRPS Act 2020 is received in your inbox. Kindly login and process";
//            String mnumber = appobj.getContact();
//            sms.sendSMSs(message, mnumber, templateid);
//
//            EmailServices em = new EmailServices();
//            em.sendEmailAPI(appobj.getEmail(), "Appeal Received in Inbox", message);

        }
        T_userlogin forwardedUser = appeal.getForwardusercode();
        if(forwardedUser != null){
            res = "Failed. Unable to recall the appeal";
        }else{
            res = "Recalled Appeal Successfully";
        }

        return res;
    }

    @GetMapping("/appealsdashboard")
    public String appealDashboard(Model model) {

//        String page = "pages/secure/Subordinate/createdosubordinate";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();

        Object[] appealsCount = appealsRepository.findAppealStatisticsByAppelateId(user.getAppelateid().getAppelateid());
        List<Object[]> app = appealsRepository.findAppelateStatisticsByAppelateId(user.getAppelateid().getAppelateid());

        // Printing appealsCount
        // Check if appealsCount[0] is an Object array
        List<Object> appealDataList = new ArrayList<>();

        if (appealsCount[0] instanceof Object[]) {
            // Flatten the first (and only) inner array
            Object[] innerArray = (Object[]) appealsCount[0];

            // Add each element of the inner array to appealDataList
            for (Object element : innerArray) {
                appealDataList.add(element);
            }
        }

        // Debug print the flattened list
//        System.out.println("Appeal Data List: " + appealDataList);




        // Printing app list
//        System.out.println("\nData in app (reportlist):");
//        for (int i = 0; i < app.size(); i++) {
//            Object[] row = app.get(i);
//            System.out.println("Row " + (i + 1) + ":");
//            for (int j = 0; j < row.length; j++) {
//                System.out.println("   Column " + j + ": " + row[j]);
//            }
//        }






        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("jdata", appealDataList);
        model.addAttribute("reportlist", app);




        return "pages/secure/dashboards/appealsdashboard";
    }




    public Date convertStringToDate(String dateStr) {
        // Define the date format you expect
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            // Parse the string to a Date object
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception, possibly return null or throw an application-specific exception
        }
        return date;
    }


}
