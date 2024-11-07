package com.rpsa.rpsa.controller;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.rpsa.rpsa.dto.AppealDTO;
import com.rpsa.rpsa.dto.AppealProcessDTO;
import com.rpsa.rpsa.dto.AppealProcessResponseDTO;
import com.rpsa.rpsa.dto.DoAlertDTO;
import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.repository.*;
import com.rpsa.rpsa.service.*;
import com.rpsa.rpsa.utils.BharatVC;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/secure")
public class AppealController {

    @Autowired
    private M_ServicesService servicesService;

    @Autowired
    private T_UserService userService;

    @Autowired
    private M_SubservicesService subServicesService;

    @Autowired
    private M_DesignatedOfficesService doService;

    @Autowired
    private M_AppealGroundRepository appealGroundRepository;

    @Autowired
    private M_AppelateRepository appelateRepository;

    @Autowired
    private TAppealsRepository appealsRepository;

    @Autowired
    private T_AppealsService appealService;

    @Autowired
    private M_PaymentRepository payRepo;

    @Autowired
    private T_PaymentsRepository tPayRepo;

    @Autowired
    private M_DesignatedOfficesRepository doRepo;

    @Autowired
    private M_ActionRepository actionRepo;

    @Autowired
    private M_RemarksRepository remarkRepo;

    @Autowired
    private T_TransactionssRepository transactionRepo;

    @Autowired
    private T_userloginRepository usersRepo;

    @Autowired
    private T_TransactionssService transService;

    @Autowired
    private M_StatusRepository statusRepo;

    @Autowired
    private Audit_TrailRepository auditRepo;

    @Autowired
    private ProcessService processService;
    @Autowired
    private M_SubservicesRepository m_SubservicesRepository;
    @Autowired
    private T_DOAlertsRepository t_DOAlertsRepository;


    @GetMapping("/fileappeals")
    public String fileAppeals(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);


        List<M_Services> serviceList = servicesService.getServiceList();

        serviceList = serviceList.stream()
                .sorted(Comparator.comparing(M_Services::getServicename))
                .collect(Collectors.toList());

        List<M_AppealGround> appealGrounds = appealGroundRepository.findAll();

        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        model.addAttribute("processes", process);


        model.addAttribute("userData", user);

        model.addAttribute("servicelist", serviceList);
        model.addAttribute("appealGrounds", appealGrounds);


        return "/pages/secure/fileappeals";
    }


    @GetMapping("/getSubServices/{serviceCode}")
    @ResponseBody
    public List<M_Subservices> getSubServices(@PathVariable("serviceCode") String serviceCode) {
        List<M_Subservices> subServices = subServicesService.findByServicecode(serviceCode);
        return subServices;
    }




    @GetMapping("/getDesignatedOffices/{subserviceCode}")
    @ResponseBody
    public List<Map<String, Object>> getDesignatedOffices(@PathVariable String subserviceCode) {
        return doService.getDesignatedOfficesBySubserviceCode(subserviceCode);
    }


    @GetMapping("/getAppellateId")
    @ResponseBody
    public String getAppellateId(@RequestParam String doSelectedValue, @RequestParam String subServiceValue) {
        String appellateId = appelateRepository.findAppellateIdBySubserviceCodeAndOfficeId(doSelectedValue, subServiceValue);
        System.out.println("appellateId " + appellateId);
        return appellateId;
    }




    @PostMapping("/appealSubmission")
    @ResponseBody
    public String submitAppeal(
            @ModelAttribute AppealDTO appealDto,
            @RequestParam(value = "ordercopy", required = false) MultipartFile ordercopy,
            @RequestParam("idproof") MultipartFile idproof,
            @RequestParam("supportdoc") MultipartFile supportdoc,
            @RequestParam(value = "supportdoc2", required = false) MultipartFile supportdoc2,
            @RequestParam(value = "supportdoc3", required = false) MultipartFile supportdoc3,
            @RequestParam(value = "supportdoc4", required = false) MultipartFile supportdoc4,
            @RequestParam(value = "supportdoc5", required = false) MultipartFile supportdoc5,
            @RequestParam("form1doc") MultipartFile form1doc,
            RedirectAttributes redirectAttributes) {

        // Convert files to byte array here or handle them as per your need
        // For example:
        try {
            byte[] idProofBytes = idproof.getBytes();
            byte[] supportDocBytes = supportdoc.getBytes();
            byte[] form1DocBytes = form1doc.getBytes();

            // Optional documents
            byte[] orderCopyBytes = ordercopy == null ? null : ordercopy.getBytes();
            byte[] supportDoc2Bytes = supportdoc2 == null ? null : supportdoc2.getBytes();
            byte[] supportDoc3Bytes = supportdoc3 == null ? null : supportdoc3.getBytes();
            byte[] supportDoc4Bytes = supportdoc4 == null ? null : supportdoc4.getBytes();
            byte[] supportDoc5Bytes = supportdoc5 == null ? null : supportdoc5.getBytes();

            String res = "1";

            // Process your DTO and files
            T_Appeals newAppeal = appealService.submitAppeal(appealDto);

            T_Transactionss newTransactions = transService.submitTransactions();

            if(newAppeal != null && newAppeal.getAppealcode() != null){
                newAppeal.setIdproof(idProofBytes);
                newAppeal.setSupportdoc(supportDocBytes);
                newAppeal.setForm1doc(form1DocBytes);
                if (orderCopyBytes.length > 0) {
                    newAppeal.setOrdercopy(orderCopyBytes);
                }
                if (supportDoc2Bytes.length > 0) {
                    newAppeal.setSupportdoc2(supportDoc2Bytes);
                }
                if (supportDoc3Bytes.length > 0) {
                    newAppeal.setSupportdoc3(supportDoc3Bytes);
                }
                if (supportDoc4Bytes.length > 0) {
                    newAppeal.setSupportdoc4(supportDoc4Bytes);
                }
                if (supportDoc5Bytes.length > 0) {
                    newAppeal.setSupportdoc5(supportDoc5Bytes);
                }

                // Save the appeal to the database
                appealsRepository.save(newAppeal);
                newTransactions.setAppealcode(newAppeal);
                transactionRepo.save(newTransactions);


                res = newAppeal.getAppealcode();
            }
            else {
                res = "Unable to proceed your appeal. Please try again!";
            }
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            // Return error response
            return "Error occurred while submitting appeal: " + e.getMessage();
        }
    }

    @GetMapping("/initiatepayment/{pathVar}")
    public String initiatePayment(@PathVariable String pathVar, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        model.addAttribute("userData", user);
        T_Appeals appealsData = appealsRepository.findById(pathVar).orElse(null);
        model.addAttribute("appealsData", appealsData);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        model.addAttribute("processes", process);

        return "/pages/secure/initiatepayment";
    }


    @PostMapping("/initiate")
    @ResponseBody
    public String initiate(String appealcode, Model model) throws JsonProcessingException {

        //Create object of ServiceGRAS Gateway
        ServiceGRASGateway gras = new ServiceGRASGateway();


        //Getting the LoggedIn user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);


        //Creating payid
        String payid = "1";

        M_Payment pay = payRepo.findById(payid).orElse(null);
        String params = gras.generateRedirectURI(user, pay.getAmt(), appealcode);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode appdata = objectMapper.readTree(params);
        System.out.println(appdata); // Use the JSON object as needed


        Visitors.T_Payments transaction = new Visitors.T_Payments();

        transaction.setTransactioncode(appdata.get("DEPARTMENT_ID").textValue());
        T_Appeals appealss = appealsRepository.findByRefno(appealcode);


        transaction.setAppealcode(appealss);
        transaction.setUsercode(user);
        transaction.setAmount(appdata.get("AMOUNT1").textValue());
        transaction.setPaymentstatus("I");
        transaction.setSentparameters(params);
        transaction.setEntrydate(new Date());

        tPayRepo.save(transaction);

        return params;


    }


    @GetMapping("/uinbox")
    public String getUserInbox(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        List<T_Appeals> appeals = appealsRepository.findAllByUsercode(user);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        model.addAttribute("processes", process);

        model.addAttribute("userData", user);
        model.addAttribute("appeal", appeals);

        return "/pages/secure/inbox/uinbox";
    }


    @GetMapping("/appealstatus/{appealcode}")
    public String getAppealStatus(@PathVariable String appealcode,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        model.addAttribute("processes", process);
        T_Appeals appeals = appealsRepository.findById(appealcode).orElse(null);
        List<T_Transactionss> allTransaction = transactionRepo.findByAppealCodeOrderedByTransactionCodeDesc(appealcode);
        List<T_userlogin> doSubOrdinates = usersRepo.findAllByOfficeidAndUserrole_roleid(user.getOfficeid(), "10");
        System.out.println("doSubOrdinates  - " + doSubOrdinates);
        model.addAttribute("userData", user);
        model.addAttribute("appeals", appeals);
        model.addAttribute("transactionlist", allTransaction);
        model.addAttribute("dosublist", doSubOrdinates);

        return "/pages/secure/appealStatus/appealstatus";
    }



    @GetMapping("/aainbox")
    public String appellateInbox(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        String appelateid = user.getAppelateid().getAppelateid();


        List<T_Appeals> getAppelateAppeals = appealsRepository.getAppelateAppeals(appelateid);

        List<T_Appeals> getProcessedAppelateAppeals = appealsRepository.getProcessedAppelateAppeals(appelateid);

        List<T_Appeals> getForwardedAppeals = appealsRepository.getForwardedAppeals(appelateid);

        List<T_Appeals> getForwardedDAppeals = appealsRepository.getForwardedDAppeals(appelateid);

        List<T_Appeals> getAppelateDirections = appealsRepository.getAppelateDirections(appelateid);

        List<T_Appeals> getAppelatePending = appealsRepository.getAppelatePending(appelateid);

        List<T_Appeals> getAppelateDisposed = appealsRepository.getAppelateDisposed(appelateid);

        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        List<String> servicesList = appelateRepository.findServicesListByAppelateid(appelateid);

        List<String> uniqueServices = servicesList.stream()
                .map(serviceId -> m_SubservicesRepository.findById(serviceId)
                        .orElseThrow(() -> new EntityNotFoundException("Not found !")))
                .filter(service -> service.getServicecode() != null)
                .map(service -> service.getServicecode().getServicename())
                .collect(Collectors.toSet()) // Collect unique service names in a Set
                .stream()
                .collect(Collectors.toList());



        model.addAttribute("processes", process);



        model.addAttribute("userData", user);
        model.addAttribute("newAppeals", getAppelateAppeals.size());
        model.addAttribute("processedAppeals", getProcessedAppelateAppeals.size());
        model.addAttribute("forwardedAppeals", getForwardedAppeals.size());
        model.addAttribute("forwardedDAppeals", getForwardedDAppeals.size());
        model.addAttribute("appelateDirections", getAppelateDirections.size());
        model.addAttribute("pendingAppeals", getAppelatePending.size() + getForwardedAppeals.size());
        model.addAttribute("disposedAppeals", getAppelateDisposed.size());
        model.addAttribute("totalAppeals", getAppelateAppeals.size() + getProcessedAppelateAppeals.size() + getForwardedAppeals.size() + getAppelateDisposed.size());

        model.addAttribute("newAppealsList", getAppelateAppeals);
        model.addAttribute("processedAppealsList", getProcessedAppelateAppeals);
        model.addAttribute("forwardedAppealsList", getForwardedAppeals);
        model.addAttribute("forwardedDAppealsList", getForwardedDAppeals);
        model.addAttribute("appelateDirectionsList", getAppelateDirections);
//        model.addAttribute("pendingAppealsList", getAppelatePending);
        model.addAttribute("disposedAppealsList", getAppelateDisposed);
        model.addAttribute("subServiceList", uniqueServices);


        return "/pages/secure/inbox/aainbox";
    }

    @GetMapping("/aaservicesdashboard")
    public String aaServicesDashboard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        String appelateid = user.getAppelateid().getAppelateid();



        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        List<String> servicesList = appelateRepository.findServicesListByAppelateid(appelateid);

        List<String> uniqueServices = servicesList.stream()
                .map(serviceId -> m_SubservicesRepository.findById(serviceId)
                        .orElseThrow(() -> new EntityNotFoundException("Not found !")))
                .filter(service -> service.getServicecode() != null)
                .map(service -> service.getServicecode().getServicename())
                .collect(Collectors.toSet()) // Collect unique service names in a Set
                .stream()
                .collect(Collectors.toList());

//        for (String services : servicesList){
//            M_Subservices service = m_SubservicesRepository.findById(services).orElseThrow(() -> new EntityNotFoundException("Not found !"));
//            if(service != null){
//                String serviceName = service.getServicecode().getServicename();
//                sub.add(serviceName);
//            }
//        }

//        System.out.println(sub);


        model.addAttribute("processes", process);



        model.addAttribute("userData", user);
        model.addAttribute("subServiceList", uniqueServices);


        return "/pages/secure/dashboards/aaServicesDashboard";
    }


    @GetMapping("/aaview/{appealcode}")
    public String getAppealStatusView(@PathVariable String appealcode, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        T_Appeals appeals = appealsRepository.findById(appealcode).orElse(null);

        M_Designatedoffices dos = doRepo.findById(appeals.getOfficeid().getOfficeid()).orElse(null);

        List<M_Action> action = actionRepo.findAllByFlagAndActioncode();

        List<M_Remarks> remarks = remarkRepo.findAll();

        List<T_Transactionss> transactions = transactionRepo.findByAppealCodeOrderedByTransactionCodeDesc(appealcode);

        List<T_userlogin> activeUser = usersRepo.findActiveUsersByAppelateId(user.getAppelateid().getAppelateid());


        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        model.addAttribute("processes", process);

        System.out.println("ACTIONS ARE -" + action);




        model.addAttribute("userData", user);
        model.addAttribute("appeallist", appeals);
        model.addAttribute("processappeal", appeals);
        model.addAttribute("dos", dos);
        model.addAttribute("maction", action);
        model.addAttribute("remarkslist", remarks);
        model.addAttribute("transactionlist", transactions);
        model.addAttribute("userslist", activeUser);

        return "/pages/secure/views/aaview";
    }


    @PostMapping("/saveforwardedusers/{appealcode}")
    @ResponseBody
    public String saveforwardedusers(String appjson, @PathVariable String appealcode) throws ParseException, IOException {

        String res = "-1";



        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode appdata = objectMapper.readTree(appjson);




        String fusercode = appdata.get("fusercode").asText();

        String forwardremarkstxt = appdata.get("forwardremarkstxt").asText();






        //Creating one user with name appobj and setting the "fusercode" gettig from frontend part. So that we can set forwardusercode to thr forwared p user
        T_userlogin appobj = usersRepo.findById(fusercode).orElse(null);

        System.out.println("Forward User Data - " + appobj);




        T_Appeals appeal = appealsRepository.findById(appealcode).orElse(null);

        M_Status s = statusRepo.findById("1").orElse(null);

        M_Action ac = actionRepo.findById("4").orElse(null);


        if(appeal != null) {
            // Update fields of the appeal entity
            appeal.setForwardusercode(appobj);
            appeal.setStatusid(s);
            appeal.setAppelateactioncode(ac);
            appeal.setAppelatedate(new Date());
            appeal.setLastactiondate(new Date());
            appeal.setForwardremarks(forwardremarkstxt);

            // Save the updated appeal entity
            appealsRepository.save(appeal);


            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            T_userlogin user = userService.findByUsername(username);


            System.out.println(appdata.get("fusercode").toString());
            System.out.println(fusercode);

            appobj = usersRepo.findById(fusercode).orElse(null);


            System.out.println("Forwarded to " + appobj.getFullname());


            //        appobj = usersRepo.(appdata.get("fusercode").toString());

            System.out.println(appeal.getAppealcode());

            T_Transactionss t = new T_Transactionss();


            Integer transMaxId = transactionRepo.findMaxUnique();
            if (transMaxId == null) {
                transMaxId = 0; // Starting id if no records exist
            }

            int newTransMaxId = transMaxId + 1;
            String stringTransMaxId = String.valueOf(newTransMaxId);


            t.setTransactionscode(stringTransMaxId);

            t.setAppealcode(appeal);
            t.setAppeallevel(appeal.getAppeallevel());
            t.setActioncode(ac);
            t.setTransactiondate(new Date());
            t.setUsercode(user);
            t.setTransactiondetails("Forwarded to " + appobj.getFullname());
            t.setRemarks(forwardremarkstxt);

            System.out.println(t.toString());

            transactionRepo.save(t);

//            if (!res.equals("-1")) {
//                Audit_Trail at = new Audit_Trail();
//                at.setIpaddress(request.getRemoteAddr());
//                at.setUrl(request.getRequestURL().toString());
//                at.setActions("Forwarded Appeal No: " + appeal.getAppealcode());
//                at.setDatatime(new Date());
//                at.setUsercode(userlogin);
//                logindao.saveaudittrail(at);
//                Sms sms = new Sms();
//                String templateid = "1407165164689355591";
//                String message = "Dear Official, an appeal " + appeal.getAppealcode() + " under MRPS Act 2020 is received in your inbox. Kindly login and process";
//                String mnumber = appobj.getContact();
//                sms.sendSMSs(message, mnumber, templateid);
//
//                EmailServices em = new EmailServices();
//                em.sendEmailAPI(appobj.getEmail(), "Appeal Received in Inbox", message);
//
//            }


        }
        else{
            res = "Appeal Not Found";
        }


        return res;
    }


    @PostMapping("/processAppeal/{appealCode}")
    @ResponseBody
    public AppealProcessResponseDTO processAppeal(@ModelAttribute AppealProcessDTO appealProcessDTO,
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

                if(appealProcessDTO.getAppelateactioncode().equals("1")){
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
                else if(appealProcessDTO.getAppelateactioncode().equals("2")){
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
                else if(appealProcessDTO.getAppelateactioncode().equals("3")){
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
                M_Status status = new M_Status();

                if (appealProcessDTO.getAppelateactioncode().equals("1")) {
                    status.setStatusid("2");
                } else if (appealProcessDTO.getAppelateactioncode().equals("2")) {
                    status.setStatusid("3");
                    if (appeal.getAppeallevel().equals("2")) {
                        appeal.setDoaction("1");
                    }
                } else if (appealProcessDTO.getAppelateactioncode().equals("3")) {
                    status.setStatusid("5");
                    if (appeal.getAppeallevel().equals("2")) {
                        appeal.setDoaction("1");
                    }
                }
                appeal.setStatusid(status);

                M_Action action = new M_Action();
                action.setActioncode(appealProcessDTO.getAppelateactioncode());
                appeal.setAppelateactioncode(action);
                appeal.setLastactiondate(new Date());
                action = actionRepo.findById(appealProcessDTO.getAppelateactioncode()).orElse(null);

                T_Transactionss t = new T_Transactionss();
                if (file1.getBytes().length > 0) {
                    t.setOrderdoc(file1.getBytes());
                }
                t.setRemarks(appealProcessDTO.getRemarkstxt());

                if(appealProcessDTO.getAppelateactioncode().equals("1")) {
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
                if (appealProcessDTO.getAppelateactioncode().equals("1") && appealProcessDTO.getHearingtype().equals("2")) {

                    t.setHearingpw(appeal.getHearingpw1());
                } else if (appealProcessDTO.getAppelateactioncode().equals("1") && appealProcessDTO.getHearingtype().equals("1")) {
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


                if (appealProcessDTO.getAppelateactioncode().equals("1")) {
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
                else if (appealProcessDTO.getAppelateactioncode().equals("3") || appealProcessDTO.getAppelateactioncode().equals("2")) {
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


    @PostMapping("/sendAlertToDo")
    @ResponseBody
    public String sendAlertToDo(@RequestBody DoAlertDTO doAlert){
        String res = "intiated";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);


        T_DOAlerts alert = new T_DOAlerts();
        Integer alertMaxId = t_DOAlertsRepository.findMaxId();

        if (alertMaxId == null) {
            alertMaxId = 0; // Starting id if no records exist
        }

        int newAlertMaxId = alertMaxId + 1;
        String stringMaxId = String.valueOf(newAlertMaxId);
        alert.setAlertid(stringMaxId);
        alert.setAlertcontent(doAlert.getContent());
        alert.setAlertdate(new Date());
        T_Appeals appeal = appealsRepository.findById(doAlert.getAppealcode()).orElseThrow(() -> new EntityNotFoundException("Appeal not found !"));
        alert.setAppealcode(appeal);
        alert.setUsercode(user);
        M_Designatedoffices doData = doRepo.findById(doAlert.getOfficeid()).orElseThrow(() -> new EntityNotFoundException("DO Not Found!"));
        alert.setOfficeid(doData);
        t_DOAlertsRepository.save(alert);
        boolean check = t_DOAlertsRepository.existsById(alert.getAlertid());
        if (check) {
            //TODO - Need to send an email to the concern Designated-officer that there is an appeal against him
            res = "Alert sent successfully";
        } else {
            res = "Failed to send alert";
        }

        return res;

    }


    @GetMapping("/appealProcessSuccess")
    public String appealActionSuccess(HttpSession session, Model model) {
        AppealProcessResponseDTO responseDTO = (AppealProcessResponseDTO) session.getAttribute("appealProcessData");
        System.out.println("Session data Return - " + responseDTO);
        model.addAttribute("appealActionSuccess", responseDTO);
        T_userlogin user = responseDTO.getUserData();
        model.addAttribute("userData", user);

        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        model.addAttribute("processes", process);
        return "/pages/secure/confirmations/appealprocesssuccess";
    }

    @GetMapping("/vclist")
    public String getVcList(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

//        List<T_Transactionss> ser = null;
        List<T_Transactionss> ser2 = null;

        Date today = new Date(); // Current date
        String actionCode = "1";

        List<T_Transactionss> ser = transactionRepo.findByUsercodeAndActioncode_ActioncodeAndVclinkIsNotNullAndHearingdateAfter(user, actionCode, today);

//        if (user.getUserrole().getRoleid().equals("3")) {
////            ser = transactionRepo.getupcomingvcs(d);
//            ser = transactionRepo.findByUsercodeAndActioncode_ActioncodeAndVclinkIsNotNullAndHearingdateAfter(
//                    user, actionCode, today);
////            ser2 = transactionRepo.getcommissionupcomingvcs(d);
//        } else {
////            ser = transactionRepo.getupcomingvcs(user.getUsercode());
//        }


        model.addAttribute("processes", process);

        model.addAttribute("userData", user);
        model.addAttribute("appeallist", ser);
        model.addAttribute("comappeallist", ser2);

        return "/pages/secure/vcList/vclist";
    }






}
