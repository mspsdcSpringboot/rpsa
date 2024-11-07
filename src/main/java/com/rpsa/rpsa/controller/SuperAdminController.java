package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.dto.*;
import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.repository.*;
import com.rpsa.rpsa.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/secure")
public class SuperAdminController {
    @Autowired
    private T_UserService userService;

    @Autowired
    private M_processesRepository processRepo;

    @Autowired
    private ProcessService processService;

    @Autowired
    private UserRoleRepository userRoleRepo;

    @Autowired
    private Audit_TrailRepository auditRepo;
    @Autowired
    private M_AppelateRepository m_AppelateRepository;

    @Autowired
    private M_DepartmentRepository deptRepo;

    @Autowired
    private M_DesignatedOfficesRepository designatedOfficesRepo;

    @Autowired
    private M_ServicesRepository serviceRepo;

    @Autowired
    private M_SubservicesRepository subServiceRepo;

    @Autowired
    private LoadHomeService loadHomeService;

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private WhatsnewRepository wNewRepo;

    @Autowired
    private WhatsNewService wNewService;

    @Autowired
    public LoadNotifications loadNotifications;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private LoadAboutUs loadAboutUs;

    @Autowired
    private PresentationsRepository presentationRepo;

    @Autowired
    private MeetingsRespository meetingsRepo;

    @Autowired
    private M_notifiedservicescountRepository nsCountRepo;

    @Autowired
    private OnlineServicesRepository onlineServicesRepository;

    @Autowired
    private M_OnlineServicesDepartmentRepository onlineDept;

    @Autowired
    private GalleryHomePageRepository galleryHomeRepo;

    @Autowired
    private GalleryAlbumsRepository albumRepo;

    @Autowired
    private GalleryPhotosRepository gPhotoRepo;

    @Autowired
    private UserActiveDurationRepository uActiveRepo;




    @GetMapping("/mapprocess")
    public String mapprocessPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        List<M_Processes> processList = processRepo.findAll();
        List<UserRole> roleList = userRoleRepo.findAll();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("rolelist", roleList);


        return "pages/secure/superAdmin/mapprocess";
    }

    @GetMapping("/getmappedprocess")
    @ResponseBody
    public List<?> getMappedData(String roleid) {

        System.out.println("roleid: " + roleid);
        List<?> process = processService.findProcessessByRoleId(roleid);
        System.out.println(process);
        return process;
    }

    @PostMapping("/saveprocessmap")
    @ResponseBody
    public String saveProcessmap(@RequestBody List<SaveProcessDTO> datas) {

        String res = "initiated";
        System.out.println("Datas : " + datas);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        String roleid = datas.get(0).getRoleid();
        System.out.println("roleid" + roleid);

        List<String> checkProcess = processRepo.getProcessessByRoleId(roleid);

        if (!checkProcess.isEmpty()) {
            processRepo.deleteUsingRoleid(roleid);
            List<String> checkProcessList = processRepo.getProcessessByRoleId(roleid);

            System.out.println("checkProcessList" + checkProcessList);


            if (checkProcessList.isEmpty()) {
                for (SaveProcessDTO data : datas) {
                    String roleId = data.getRoleid();
                    String processId = data.getProcessid();
                    processRepo.insertProcessRole(processId, roleId);
                }
            } else {
                res = "failed";
            }
        } else {
            for (SaveProcessDTO data : datas) {
                String roleId = data.getRoleid();
                String processesId = data.getProcessid();
                processRepo.insertProcessRole(processesId, roleId);
            }
        }


        List<String> checkProcessList2 = processRepo.getProcessessByRoleId(roleid);

        if (!checkProcessList2.isEmpty()) {
            res = "updated";
        } else {
            res = "failed";
        }

        return res;


    }

    @GetMapping("/viewaudittrail")
    public String viewAuditTrail(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        List<M_Processes> processList = processRepo.findAll();
        List<Audit_Trail> auditList = auditRepo.findAll();


        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("audittraillist", auditList);


        return "pages/secure/superAdmin/viewaudittrail";
    }

    @GetMapping("/addappelate")
    public String addAppelate(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        List<M_Processes> processList = processRepo.findAll();
        List<M_Appelate> appelateList = m_AppelateRepository.findAll();
        List<M_department> deptList = deptRepo.findAll();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("appelatelist", appelateList);
        model.addAttribute("departmentlist", deptList);


        return "pages/secure/superAdmin/addappelate";
    }

    @GetMapping("/getappelate/{appelateId}")
    @ResponseBody
    public M_Appelate getAppelate(@PathVariable String appelateId) {
        System.out.println("appelateId: ");
        System.out.println(appelateId);
        M_Appelate mAppelate = m_AppelateRepository.findById(appelateId).orElse(null);
        System.out.println(mAppelate);
        return mAppelate;
    }


    @GetMapping("/getdept")
    @ResponseBody
    public List<M_department> getDept() {
        List<M_department> dept = deptRepo.findAll();
        System.out.println(dept);
        return dept;
    }

    @DeleteMapping("/deleteappelate/{appelateId}")
    @ResponseBody
    public String deleteAppelate(@PathVariable String appelateId) {
        String res = "initiated";
        System.out.println("appelateId: " + appelateId);
        m_AppelateRepository.deleteById(appelateId);
        M_Appelate mAppelate = m_AppelateRepository.findById(appelateId).orElse(null);
        if (mAppelate == null) {
            res = "deleted";
        } else {
            res = "failed";
        }
        return res;
    }

    @PostMapping("/updateappelate")
    @ResponseBody
    public String updateAppelateData(@RequestBody AppelateDTO appellateData) {
        System.out.println("jkhruirv");
        System.out.println("jkhruirv" + appellateData);
        String res = "initiated";
        M_Appelate mAppelate = m_AppelateRepository.findById(appellateData.getAppelateid()).orElse(null);
        if (mAppelate != null) {
            M_department dept = deptRepo.findById(appellateData.getDepartmentcode()).orElse(null);
            mAppelate.setOfficename(appellateData.getAppelatename());
            mAppelate.setDepartmentcode(dept);
            mAppelate.setOfficelevel(appellateData.getOfficelevel());
            mAppelate.setSubmission_location_id(mAppelate.getSubmission_location_id());
            System.out.println(mAppelate);
            m_AppelateRepository.save(mAppelate);
            res = "updated";
        } else {
            res = "failed";
        }
        return res;
    }

    @PostMapping("/addappelate")
    @ResponseBody
    public String addAppelate(@RequestBody AppelateDTO appellateData) {

        System.out.println("jkhruirv" + appellateData);
        String res = "initiated";
        M_Appelate mAppelate = new M_Appelate();

        Integer appelateId = m_AppelateRepository.findMaxId();

        if (appelateId == null) {
            appelateId = 0; // Starting id if no records exist
        }

        int newAppellateMaxId = appelateId + 1;
        String stringMaxId = String.valueOf(newAppellateMaxId);
        mAppelate.setAppelateid(stringMaxId);


        M_department dept = deptRepo.findById(appellateData.getDepartmentcode()).orElse(null);
        mAppelate.setOfficename(appellateData.getAppelatename());
        mAppelate.setDepartmentcode(dept);
        mAppelate.setOfficelevel(appellateData.getOfficelevel());
        mAppelate.setSubmission_location_id(mAppelate.getSubmission_location_id());
        System.out.println(mAppelate);
        m_AppelateRepository.save(mAppelate);
        M_Appelate appelate = m_AppelateRepository.findById(stringMaxId).orElse(null);
        if (appelate != null) {
            res = "added";
        } else {
            res = "failed";
        }
        return res;
    }


    @GetMapping("/adddos")
    public String addDo(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        List<M_Processes> processList = processRepo.findAll();

        List<M_Designatedoffices> officeList = designatedOfficesRepo.findAll();
//        List<M_Appelate> appelateList = m_AppelateRepository.findAll();
//        List<M_department> deptList = deptRepo.findAll();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("dolist", officeList);
//        model.addAttribute("departmentlist", deptList);

        return "pages/secure/superAdmin/adddos";
    }

    @GetMapping("/getdos/{officeId}")
    @ResponseBody
    public M_Designatedoffices getDos(@PathVariable String officeId) {
        System.out.println("appelateId: ");
        System.out.println(officeId);
        M_Designatedoffices office = designatedOfficesRepo.findById(officeId).orElse(null);
        System.out.println(office);
        return office;
    }

    @PostMapping("/updatedos")
    @ResponseBody
    public String updateDo(@RequestBody DoDTO doData) {
        System.out.println("jkhruirv");
        System.out.println("jkhruirv" + doData);
        String res = "initiated";
        M_Designatedoffices office = designatedOfficesRepo.findById(doData.getOfficeid()).orElse(null);

        if (office != null) {
            office.setOfficename(doData.getOfficename());
            office.setOfficername(doData.getOfficername());
            office.setOfficername(doData.getOfficername());
            office.setEmail(doData.getEmail());
            office.setMobile(doData.getMobile());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            office.setUsername(username);
            office.setSubmission_location_id(office.getSubmission_location_id());
            office.setCreated(office.getCreated());
            designatedOfficesRepo.save(office);
            res = "updated";
        } else {
            res = "failed";
        }
        return res;
    }

    @PostMapping("/adddos")
    @ResponseBody
    public String addDos(@RequestBody DoDTO doDto) {

        System.out.println("jkhruirv" + doDto);
        String res = "initiated";
        M_Designatedoffices office = new M_Designatedoffices();

        Integer ofId = designatedOfficesRepo.findMaxId();

        if (ofId == null) {
            ofId = 0; // Starting id if no records exist
        }

        int newOfMaxId = ofId + 1;
        String stringMaxId = String.valueOf(newOfMaxId);

        office.setOfficeid(stringMaxId);
        office.setOfficename(doDto.getOfficename());
        office.setOfficername(doDto.getOfficername());
        office.setEmail(doDto.getEmail());
        office.setMobile(doDto.getMobile());
        office.setCreated("1");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        office.setUsername(username);
        office.setSubmission_location_id(null);
        designatedOfficesRepo.save(office);
        M_Designatedoffices officeCheck = designatedOfficesRepo.findById(office.getOfficeid()).orElse(null);
        if (officeCheck != null) {
            res = "added";
        } else {
            res = "failed";
        }
        return res;
    }

    @GetMapping("/addservices")
    public String addService(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());

        List<M_Processes> processList = processRepo.findAll();
        List<M_Services> serviceList = serviceRepo.findAll();


        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("serviceslist", serviceList);


        return "pages/secure/superAdmin/addservices";
    }

    @GetMapping("/getsubservices/{servicecode}")
    @ResponseBody
    public List<M_Subservices> getServices(@PathVariable String servicecode) {
        List<M_Subservices> servicesList = subServiceRepo.findByServicecodeServicecode(servicecode);
        System.out.println(servicesList);
        return servicesList;
    }

    @PostMapping("/saveservices")
    @ResponseBody
    public String saveServices(@RequestBody List<SubmitServiceDTO> serviceData) {
        String res = "Iniitated";
        System.out.println("Inside save services");
        System.out.println("serviceData " + serviceData.get(0).getServicecode().isEmpty());

        if (serviceData.get(0).getServicecode().isEmpty()) {
            M_Services services1 = new M_Services();

            Integer serviceIds = serviceRepo.findMaxId();

            if (serviceIds == null) {
                serviceIds = 0; // Starting id if no records exist
            }

            int newServiceMaxId = serviceIds + 1;
            String stringServiceMaxId = String.valueOf(newServiceMaxId);

            services1.setServicecode(stringServiceMaxId);
            services1.setServicename(serviceData.get(0).getServicename());
            serviceRepo.save(services1);

            for (SubmitServiceDTO service : serviceData) {


                M_Subservices subService = new M_Subservices();

                Integer subServiceId = subServiceRepo.findMaxId();

                if (subServiceId == null) {
                    subServiceId = 0; // Starting id if no records exist
                }

                int newSubServiceMaxId = subServiceId + 1;
                String stringSubServiceMaxId = String.valueOf(newSubServiceMaxId);

                subService.setSubservicecode(stringSubServiceMaxId);

                subService.setSubservicename(service.getSubservicename());
                subService.setStipulatedtime(service.getStipulatedtime());
                subService.setServicecode(services1);


                subServiceRepo.save(subService);
                boolean subServiceCheck = subServiceRepo.existsById(stringSubServiceMaxId);
                boolean serviceCheck = serviceRepo.existsById(stringServiceMaxId);
                if (subServiceCheck && serviceCheck) {
                    res = "added";
                } else {
                    res = "failed";
                }
            }
        } else {
            for (SubmitServiceDTO service : serviceData) {
                M_Services services = serviceRepo.findById(service.getServicecode()).orElse(null);
                assert services != null;
                services.setServicename(service.getServicename());
                serviceRepo.save(services);

                M_Subservices subService = subServiceRepo.findById(service.getSubservicecode()).orElse(null);
                if (subService != null) {
                    subService.setSubservicename(service.getSubservicename());
                    subService.setStipulatedtime(service.getStipulatedtime());
                    subServiceRepo.save(subService);
                    boolean subServiceCheck = subServiceRepo.existsById(service.getSubservicecode());
                    boolean serviceCheck = serviceRepo.existsById(service.getServicecode());
                    if (subServiceCheck && serviceCheck) {
                        res = "updated";
                    } else {
                        res = "failed";
                    }
                } else {
                    M_Subservices subService1 = new M_Subservices();
                    Integer subServiceId = subServiceRepo.findMaxId();

                    if (subServiceId == null) {
                        subServiceId = 0; // Starting id if no records exist
                    }

                    int newSubServiceMaxId = subServiceId + 1;
                    String stringSubServiceMaxId = String.valueOf(newSubServiceMaxId);

                    subService1.setSubservicecode(stringSubServiceMaxId);

                    subService1.setSubservicename(service.getSubservicename());
                    subService1.setStipulatedtime(service.getStipulatedtime());
                    subService1.setServicecode(services);
                    subServiceRepo.save(subService1);

                    boolean subServiceCheck1 = subServiceRepo.existsById(subService1.getSubservicecode());
                    boolean serviceCheck = serviceRepo.existsById(service.getServicecode());

                    res = (subServiceCheck1 && serviceCheck) ? "updated" : "failed";
                }
            }
        }

        return res;

    }



    @GetMapping("/mapservices")
    public String mapServices(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();

        List<M_Subservices> subServices = subServiceRepo.findAll();
        List<M_Designatedoffices> designatedOffices = designatedOfficesRepo.findAll();
        List<M_Appelate> appelates = m_AppelateRepository.findAll();


        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("subservicelist", subServices);
        model.addAttribute("dolist", designatedOffices);
        model.addAttribute("appelatelist", appelates);


        return "pages/secure/superAdmin/mapservices";
    }

    @GetMapping("/getmappeddata/{subservicecode}")
    @ResponseBody
    public List<DoAppellateObjectDTO> getmappeddata(@PathVariable String subservicecode) {

        System.out.println("subservicecode " + subservicecode);
//        List<DoAppellateDTO> doAppelateDatas = m_AppelateRepository.findAppellateAndDos(subservicecode);
        List<Object[]> results = m_AppelateRepository.findAppellateAndDos(subservicecode);
        List<DoAppellateDTO> dtos = new ArrayList<>();
        List<DoAppellateObjectDTO> objectList = new ArrayList<>();

        for (Object[] result : results) {
            String officeId = (String) result[0];
            String appellateId = (String) result[1];
            M_Subservices subservices = subServiceRepo.findById(subservicecode).orElse(null);
            M_Designatedoffices offices = designatedOfficesRepo.findById(officeId).orElse(null);
            M_Appelate appelate = m_AppelateRepository.findById(appellateId).orElse(null);
            objectList.add(new DoAppellateObjectDTO(subservices, offices, appelate));
            dtos.add(new DoAppellateDTO(officeId, appellateId));
        }


        System.out.println(dtos);
        System.out.println(objectList);

        return objectList;
    }

    @PostMapping("/saveappellatemap")
    @ResponseBody
    public String saveAppellateMap(@RequestBody List<SaveAppellateDTO> datas) {
        System.out.println("saveAppellateMap");
        System.out.println(datas);
        String res = "initiated";
        List<Object[]> check = m_AppelateRepository.checkExistedData(datas.get(0).getSubservicecode(), datas.get(0).getAppelateid());
        if (!check.isEmpty()) {
            for (Object data : check) {
                m_AppelateRepository.deleteAppellateMap(datas.get(0).getSubservicecode(), datas.get(0).getAppelateid());
            }
            List<Object[]> check2 = m_AppelateRepository.checkExistedData(datas.get(0).getSubservicecode(), datas.get(0).getAppelateid());
            if (check2.isEmpty()) {
                for (SaveAppellateDTO dto : datas) {
                    m_AppelateRepository.saveappelatemap(dto.getSubservicecode(), dto.getOfficeid(), dto.getAppelateid());
                    List<String> checkDos = m_AppelateRepository.findDoList(dto.getSubservicecode(), dto.getAppelateid());
                    if (!checkDos.isEmpty()) {
                        res = "updated";
                    } else {
                        res = "failed";
                    }
                }
            }
        } else {
            for (SaveAppellateDTO dto : datas) {
                m_AppelateRepository.saveappelatemap(dto.getSubservicecode(), dto.getOfficeid(), dto.getAppelateid());
                List<String> checkDos = m_AppelateRepository.findDoList(dto.getSubservicecode(), dto.getAppelateid());
                if (!checkDos.isEmpty()) {
                    res = "updated";
                } else {
                    res = "failed";
                }
            }
        }

        return res;
    }

    @GetMapping("/appelatemapdashboard")
    public String appelatemapdashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        List<M_Subservices> subServices = subServiceRepo.findAll();
        List<String> doList = m_AppelateRepository.findSubServiceList();
        List<M_Subservices> mappedSubServiceList = subServices.stream()
                .filter(subService -> doList.contains(subService.getSubservicecode()))
                .collect(Collectors.toList());

        List<M_Subservices> notMappedSubServiceList = subServices.stream()
                .filter(subService -> !doList.contains(subService.getSubservicecode()))
                .collect(Collectors.toList());


        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("mappedlist", mappedSubServiceList);
        model.addAttribute("mappedcount", mappedSubServiceList.size());
        model.addAttribute("notmappedlist", notMappedSubServiceList);
        model.addAttribute("notmappedcount", notMappedSubServiceList.size());
        return "pages/secure/superAdmin/appelatemapdashboard";

    }


    @GetMapping("/mapservices/{subservicecode}")
    public String mapServicesRedirect(@PathVariable String subservicecode, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();

        List<M_Subservices> subServices = subServiceRepo.findAll();
        List<M_Designatedoffices> designatedOffices = designatedOfficesRepo.findAll();
        List<M_Appelate> appelates = m_AppelateRepository.findAll();


        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("subservicelist", subServices);
        model.addAttribute("dolist", designatedOffices);
        model.addAttribute("appelatelist", appelates);


        return "pages/secure/superAdmin/mapservices";
    }

    @GetMapping("/managecontacts")
    public String manageContacts(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        List<Contacts> contacts = loadHomeService.getContacts();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("contactslist", contacts);

        return "pages/secure/superAdmin/managecontacts";
    }


    @PostMapping("/savecontacts")
    @ResponseBody
    public String saveContacts(@RequestBody List<Contacts> contacts) {
        System.out.println(contacts);
        String res = "initiated";
        List<Contacts> contactsdata = loadHomeService.getContacts();
        contactsRepository.deleteAll(contactsdata);
        List<Contacts> contactscheck = loadHomeService.getContacts();
        if (contactscheck.isEmpty()) {
            for (Contacts contact : contacts) {
                contactsRepository.save(contact);
                boolean checkContact = contactsRepository.existsById(contact.getContactid());
                if (checkContact) {
                    res = "updated";
                } else {
                    res = "failed";
                }
            }
        }
        return res;
    }

    @GetMapping("/managewhatsnew")
    public String whatsNew(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        List<WhatsNew> whatsnewList = loadHomeService.getNoticeBoardData();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("whatsnewlist", whatsnewList);


        return "pages/secure/superAdmin/managewhatsnew";
    }

    @PostMapping("/addwhatsnew")
    @ResponseBody
    public String saveWhatsNew(@RequestBody WhatsNew whatsnew) {
        System.out.println(whatsnew);
        String res = "initiated";
        if (whatsnew.getWhatsnewid().isEmpty()) {
            WhatsNew newWNew = wNewService.addNewWhatsNew(whatsnew);
            boolean check = wNewRepo.existsById(newWNew.getWhatsnewid());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }
        } else {
            WhatsNew updatedWNew = wNewService.updateWhatsNew(whatsnew);
            boolean check = wNewRepo.existsById(updatedWNew.getWhatsnewid());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }
        }

        return res;
    }

    @GetMapping("/getwhatsnew")
    @ResponseBody
    public WhatsNew getWhatsNew(@RequestParam String id) {
        System.out.println(id);
        WhatsNew whatsNew = wNewRepo.findById(id).orElse(null);

        System.out.println(whatsNew);
        return whatsNew;
    }

    @DeleteMapping("/deletewhatsnew")
    @ResponseBody
    public String deleteWhatsNew(@RequestParam String id) {

        String res = "initiated";
        wNewRepo.deleteById(id);
        boolean check = wNewRepo.existsById(id);
        if (check) {
            res = "Error";
        } else {
            res = "Success";
        }

        return res;
    }

    @GetMapping("/managenotifications")
    public String manageNotifications(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        List<Notification> notifications = loadNotifications.getAllNotifications();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("notificationslist", notifications);


        return "pages/secure/superAdmin/managenotifications";
    }

    @GetMapping("/getnotification")
    @ResponseBody
    public Notification getNotification(@RequestParam String notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        return notification;
    }

    @DeleteMapping("/deletenotification")
    @ResponseBody
    public String deleteNotification(@RequestParam String notificationId) {
        String res = "iniitated";
        notificationRepository.deleteById(notificationId);
        boolean check = notificationRepository.existsById(notificationId);
        if (check) {
            res = "failed";
        } else {
            res = "deleted";
        }
        return res;
    }

    @PostMapping("/addnotification")
    @ResponseBody
    public String addNotifications(@ModelAttribute NotificationDTO notification, @RequestParam("doc") MultipartFile doc) {

        System.out.println("notification " + notification);
        String res = "initalized";

        if (notification.getNotificationid().isEmpty()) {
            Notification notificationData = new Notification();

            Integer notificationId = notificationRepository.findMaxId();

            if (notificationId == null) {
                notificationId = 0; // Starting id if no records exist
            }

            int newNotificationMaxId = notificationId + 1;
            String stringNotificationMaxId = String.valueOf(newNotificationMaxId);

            notificationData.setNotificationid(stringNotificationMaxId);

            notificationData.setNotificationdate(notification.getNotificationdate());
            notificationData.setTitle(notification.getTitle());
            notificationData.setNotificationname(notification.getNotificationname());
            if (!doc.isEmpty()) {
                try {
                    byte[] bytesDoc = doc.getBytes();
                    notificationData.setDoc(bytesDoc);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file upload exception
                }
            }

            notificationRepository.save(notificationData);
            boolean check = notificationRepository.existsById(notificationData.getNotificationid());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }
        } else {
            Notification notificationData = notificationRepository.findById(notification.getNotificationid()).orElseThrow(() -> new EntityNotFoundException("Notification not found"));

            notificationData.setNotificationdate(notification.getNotificationdate());
            notificationData.setTitle(notification.getTitle());
            notificationData.setNotificationname(notification.getNotificationname());

            if (!doc.isEmpty()) {
                try {
                    byte[] bytesDoc = doc.getBytes();
                    notificationData.setDoc(bytesDoc);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file upload exception
                }
            }

            notificationRepository.save(notificationData);
            boolean check = notificationRepository.existsById(notificationData.getNotificationid());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }
        }


        return res;

    }

    @GetMapping("/managepresentations")
    public String managePresentations(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        List<Presentations> presentations = loadAboutUs.getPresentations();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("presentationlist", presentations);


        return "pages/secure/superAdmin/managepresentations";
    }

    @GetMapping("/getpresentation")
    @ResponseBody
    public Presentations getPresentation(@RequestParam String presentationId) {
        Presentations presentation = presentationRepo.findById(presentationId).orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        return presentation;
    }

    @DeleteMapping("/deletepresentation")
    @ResponseBody
    public String deletePresentation(@RequestParam String presentationId) {
        String res = "iniitated";
        presentationRepo.deleteById(presentationId);
        boolean check = presentationRepo.existsById(presentationId);
        if (check) {
            res = "Failed";
        } else {
            res = "Deleted Successfully";
        }
        return res;
    }

    @PostMapping("/addpresentation")
    @ResponseBody
    public String addPresentations(@ModelAttribute PresentationDTO presentation, @RequestParam("doc") MultipartFile doc) {

        System.out.println("presentation " + presentation);
        String res = "initalized";

        if (presentation.getPresentationid().isEmpty()) {
            Presentations presentationData = new Presentations();

            Integer notificationId = presentationRepo.findMaxId();

            if (notificationId == null) {
                notificationId = 0; // Starting id if no records exist
            }

            int newNotificationMaxId = notificationId + 1;
            String stringNotificationMaxId = String.valueOf(newNotificationMaxId);

            presentationData.setPresentationid(stringNotificationMaxId);
            presentationData.setDetails(presentation.getDetails());
            presentationData.setPresentationdate(presentation.getPresentationdate());


            if (!doc.isEmpty()) {
                try {
                    byte[] bytesDoc = doc.getBytes();
                    presentationData.setDoc(bytesDoc);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file upload exception
                }
            }

            presentationRepo.save(presentationData);
            boolean check = presentationRepo.existsById(presentationData.getPresentationid());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }
        } else {
            Presentations presentationData = presentationRepo.findById(presentation.getPresentationid()).orElseThrow(() -> new EntityNotFoundException("Presentation not found"));

            presentationData.setDetails(presentation.getDetails());
            presentationData.setPresentationdate(presentation.getPresentationdate());

            if (!doc.isEmpty()) {
                try {
                    byte[] bytesDoc = doc.getBytes();
                    presentationData.setDoc(bytesDoc);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file upload exception
                }
            }

            presentationRepo.save(presentationData);
            boolean check = presentationRepo.existsById(presentationData.getPresentationid());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }
        }
        return res;
    }

    @GetMapping("/managemeetings")
    public String manageMeetings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        List<Meetings> meetings = meetingsRepo.findAll();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("meetinglist", meetings);


        return "pages/secure/superAdmin/managemeetings";
    }


    @GetMapping("/getmeeting")
    @ResponseBody
    public Meetings getMeeting(@RequestParam String meetingId) {
        Meetings meeting = meetingsRepo.findById(meetingId).orElseThrow(() -> new EntityNotFoundException("Meeting not found"));
        return meeting;
    }

    @PostMapping("/addmeeting")
    @ResponseBody
    public String addMeeting(@ModelAttribute MeetingDTO meeting, @RequestParam("doc") MultipartFile doc) {

        System.out.println("meeting " + meeting);
        String res = "initalized";

        if (meeting.getMeetingid().isEmpty()) {
            Meetings meetingData = new Meetings();

            Integer notificationId = meetingsRepo.findMaxId();

            if (notificationId == null) {
                notificationId = 0; // Starting id if no records exist
            }

            int newNotificationMaxId = notificationId + 1;
            String stringNotificationMaxId = String.valueOf(newNotificationMaxId);

            meetingData.setMeetingid(stringNotificationMaxId);
            meetingData.setDetails(meeting.getDetails());
            meetingData.setMeetingdate(meeting.getMeetingdate());


            if (!doc.isEmpty()) {
                try {
                    byte[] bytesDoc = doc.getBytes();
                    meetingData.setDoc(bytesDoc);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file upload exception
                }
            }

            meetingsRepo.save(meetingData);
            boolean check = meetingsRepo.existsById(meetingData.getMeetingid());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }
        } else {
            Meetings meetingData = meetingsRepo.findById(meeting.getMeetingid()).orElseThrow(() -> new EntityNotFoundException("Meeting not found"));

            meetingData.setDetails(meeting.getDetails());
            meetingData.setMeetingdate(meeting.getMeetingdate());

            if (!doc.isEmpty()) {
                try {
                    byte[] bytesDoc = doc.getBytes();
                    meetingData.setDoc(bytesDoc);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file upload exception
                }
            }

            meetingsRepo.save(meetingData);
            boolean check = meetingsRepo.existsById(meetingData.getMeetingid());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }
        }
        return res;
    }

    @DeleteMapping("/deletemeeeting")
    @ResponseBody
    public String deleteMeeting(@RequestParam String meetingId) {
        String res = "iniitated";
        meetingsRepo.deleteById(meetingId);
        boolean check = meetingsRepo.existsById(meetingId);
        if (check) {
            res = "Failed";
        } else {
            res = "Deleted Successfully";
        }
        return res;
    }


    @GetMapping("/notifiedservicescount")
    public String notifiedServiceCount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        M_notifiedservicescount count = nsCountRepo.findById("1").orElseThrow(() -> new EntityNotFoundException("Notifiedservicecount not found"));

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("count", count);


        return "pages/secure/superAdmin/notifiedservicescount";
    }

    @PostMapping("/addnotifiedservicecount/{counts}")
    @ResponseBody
    public String addNotifiedServiceCount(@PathVariable String counts) {
        System.out.println("counts " + counts);
        String res = "iniitalized";
        M_notifiedservicescount count = nsCountRepo.findById("1").orElseThrow(() -> new EntityNotFoundException("Notifiedservicecount not found"));
        count.setScount(counts);
        nsCountRepo.save(count);
        res = "success";
        return res;
    }

    @GetMapping("/manageonlineservices")
    public String manageOnlineServices(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        List<OnlineServices> oServies = onlineServicesRepository.findAll();
        List<M_OnlineServicesDepartments> dept = onlineDept.findAll();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("serviceslist", oServies);
        model.addAttribute("departmentlist", dept);


        return "pages/secure/superAdmin/manageonlineservices";
    }

    @GetMapping("/getonlineservice")
    @ResponseBody
    public OnlineServices getOnlineservice(@RequestParam String Id) {
        OnlineServices onlineService = onlineServicesRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("Not Found!"));
        return onlineService;
    }


    @PostMapping("/addonlineservices")
    @ResponseBody
    public String addOnlineServices(@ModelAttribute OnlineServicesDTO online, @RequestParam("doc") MultipartFile doc) {

        System.out.println("online " + online);
        String res = "initalized";

        if (online.getSlno().isEmpty()) {
            OnlineServices onlineServices = new OnlineServices();

            Integer notificationId = onlineServicesRepository.findMaxId();

            if (notificationId == null) {
                notificationId = 0; // Starting id if no records exist
            }

            int newNotificationMaxId = notificationId + 1;
            String stringNotificationMaxId = String.valueOf(newNotificationMaxId);

            onlineServices.setSlno(stringNotificationMaxId);
            onlineServices.setServicename(online.getServicename());
            onlineServices.setSla(online.getSla());
            onlineServices.setDos(online.getDos());
            onlineServices.setAas(online.getAas());
            onlineServices.setOnline(online.getOnline());
            onlineServices.setEnclosures(online.getEnclosures());
            onlineServices.setApplyclick("0");
            M_OnlineServicesDepartments dept = onlineDept.findById(online.getDepartmentcode()).orElseThrow(() -> new EntityNotFoundException("Unable to found department!"));
            onlineServices.setDepartmentcode(dept);
            onlineServices.setDepartment(dept.getDepartmentname());
            if (!doc.isEmpty() && online.getLinks().isEmpty()) {
                String uploadDir = "E:/MSPSDC_SpringBoot/rpsa/src/main/resources/static/web/pdf/";

                // Check and create the directory if it doesn't exist
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();  // Create directory if it doesn't exist
                }

                try {
                    // Check if doc (MultipartFile) is not empty
                    if (!doc.isEmpty()) {
                        // Check if doc (MultipartFile) is not empty
                        String docFilename = System.currentTimeMillis() + "_" + doc.getOriginalFilename();

                        // Define the full path where the file will be saved
                        Path docPath = Paths.get(uploadDir, docFilename);

                        // Log file path
                        System.out.println("File path: " + docPath.toString());

                        // Save the file directly to the specified directory using transferTo
                        File destinationFile = docPath.toFile();
                        doc.transferTo(destinationFile);
                        // You can set the link or file name to the OnlineService entity for storing in the database
                        onlineServices.setLinks(docFilename);  // Assuming a field to store the file name or link in OnlineService entity
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file upload exception (optional: log the error, return an error message, etc.)
                }
            } else {
                onlineServices.setLinks(online.getLinks());
            }

            onlineServicesRepository.save(onlineServices);
            boolean check = onlineServicesRepository.existsById(onlineServices.getSlno());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }
        } else {

            OnlineServices onlineServices = onlineServicesRepository.findById(online.getSlno()).orElseThrow(() -> new EntityNotFoundException("Not Found!"));
            onlineServices.setServicename(online.getServicename());
            onlineServices.setSla(online.getSla());
            onlineServices.setDos(online.getDos());
            onlineServices.setAas(online.getAas());
            onlineServices.setOnline(online.getOnline());
            onlineServices.setEnclosures(online.getEnclosures());
            onlineServices.setApplyclick(onlineServices.getApplyclick());
            M_OnlineServicesDepartments dept = onlineDept.findById(online.getDepartmentcode()).orElseThrow(() -> new EntityNotFoundException("Unable to found department!"));
            onlineServices.setDepartmentcode(dept);
            onlineServices.setDepartment(dept.getDepartmentname());
            if (!doc.isEmpty()) {
                String uploadDir = "E:/MSPSDC_SpringBoot/rpsa/src/main/resources/static/web/pdf/";

                // Check and create the directory if it doesn't exist
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();  // Create directory if it doesn't exist
                }

                try {
                    // Check if doc (MultipartFile) is not empty
                    if (!doc.isEmpty()) {
                        // Check if doc (MultipartFile) is not empty
                        String docFilename = System.currentTimeMillis() + "_" + doc.getOriginalFilename();

                        // Define the full path where the file will be saved
                        Path docPath = Paths.get(uploadDir, docFilename);

                        // Log file path
                        System.out.println("File path: " + docPath.toString());

                        // Save the file directly to the specified directory using transferTo
                        File destinationFile = docPath.toFile();
                        doc.transferTo(destinationFile);

                        // You can set the link or file name to the OnlineService entity for storing in the database
                        onlineServices.setLinks(docFilename);  // Assuming a field to store the file name or link in OnlineService entity
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file upload exception (optional: log the error, return an error message, etc.)
                }
            } else {
                onlineServices.setLinks(online.getLinks());
            }

            onlineServicesRepository.save(onlineServices);
            boolean check = onlineServicesRepository.existsById(onlineServices.getSlno());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }

        }


        return res;

    }

    @DeleteMapping("/deleteonlineservice/{slNo}")
    @ResponseBody
    public String deleteOnlineService(@PathVariable String slNo) {
        String res = "iniitated";
        onlineServicesRepository.deleteById(slNo);
        boolean check = onlineServicesRepository.existsById(slNo);
        if (check) {
            res = "Failed";
        } else {
            res = "Deleted Successfully";
        }
        return res;
    }


    @GetMapping("/managehomepagephotos")
    public String manageHomepagePhotos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        List<GalleryHomePage> galleryPhotos = galleryHomeRepo.findAll();

        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("galleryPhotos", galleryPhotos);

        return "pages/secure/superAdmin/managehomepagephotos";
    }

    @PostMapping("/addhomepagephoto")
    @ResponseBody
    public String addHomepagePhoto(@RequestParam String photoid, @RequestParam("photodoc") MultipartFile doc) {
        String res = "initialized";
        GalleryHomePage homePhoto = galleryHomeRepo.findById(photoid).orElseThrow(() -> new EntityNotFoundException("Not Found!"));
        if (!doc.isEmpty()) {
            try {
                byte[] bytesDoc = doc.getBytes();
                homePhoto.setPhoto(bytesDoc);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle file upload exception
            }
        }
        galleryHomeRepo.save(homePhoto);
        boolean check = galleryHomeRepo.existsById(homePhoto.getPhotoid());
        if (check) {
            res = "success";
        } else {
            res = "failed";
        }
        return res;
    }

    @GetMapping("/managealbums")
    public String manageAlbums(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        List<GalleryAlbums> galleryAlbums = albumRepo.findAll();


        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("albumslist", galleryAlbums);

        return "pages/secure/superAdmin/managealbums";
    }

    @GetMapping("/getalbum/{id}")
    @ResponseBody
    public GalleryAlbums getALbum(@PathVariable String id) {
        GalleryAlbums album = albumRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Not Found!"));
        return album;
    }

    @PostMapping("/addalbum")
    @ResponseBody
    public String addAlbum(@ModelAttribute AlbumDTO album, @RequestParam("photodoc") MultipartFile doc) {

        System.out.println("album " + album);
        String res = "initalized";

        if (album.getAlbumid().isEmpty()) {
            GalleryAlbums gAlbum = new GalleryAlbums();

            Integer notificationId = albumRepo.findMaxId();

            if (notificationId == null) {
                notificationId = 0; // Starting id if no records exist
            }

            int newNotificationMaxId = notificationId + 1;
            String stringNotificationMaxId = String.valueOf(newNotificationMaxId);
            gAlbum.setAlbumid(stringNotificationMaxId);
            gAlbum.setAlbumname(album.getAlbumname());
            gAlbum.setAlbumdate(album.getAlbumdate());

            if (!doc.isEmpty()) {
                try {
                    byte[] bytesDoc = doc.getBytes();
                    gAlbum.setThumbnail(bytesDoc);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file upload exception
                }
            }

            albumRepo.save(gAlbum);
            boolean check = onlineServicesRepository.existsById(gAlbum.getAlbumid());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }
        } else {
            GalleryAlbums gAlbum = albumRepo.findById(album.getAlbumid()).orElseThrow(() -> new EntityNotFoundException("Not Found!"));
            gAlbum.setAlbumname(album.getAlbumname());
            gAlbum.setAlbumdate(album.getAlbumdate());
            if (!doc.isEmpty()) {
                try {
                    byte[] bytesDoc = doc.getBytes();
                    gAlbum.setThumbnail(bytesDoc);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file upload exception
                }
            }
            albumRepo.save(gAlbum);
            boolean check = onlineServicesRepository.existsById(gAlbum.getAlbumid());
            if (check) {
                res = "success";
            } else {
                res = "failed";
            }

        }


        return res;

    }


    @GetMapping("/managealbumphotos/{id}")
    public String manageAlbumPhotos(@PathVariable String id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        GalleryAlbums album = albumRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Not Found!"));
        List<GalleryPhotos> gPhotos = gPhotoRepo.findByAlbumid(album);

        model.addAttribute("album", id);
        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("photoslist", gPhotos);

        return "pages/secure/superAdmin/managealbumphotos";
    }

    @PostMapping("/addalbumphoto")
    @ResponseBody
    public String addAlbumPhoto(@RequestParam String albumid, @RequestParam("photodoc") List<MultipartFile> files) {
        String res = "iniitalized";
        GalleryAlbums albums = albumRepo.findById(albumid).orElseThrow(() -> new EntityNotFoundException("Not found"));
        if (albums != null) {


            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        GalleryPhotos gPhoto = new GalleryPhotos();

                        Integer notificationId = gPhotoRepo.findMaxId();

                        if (notificationId == null) {
                            notificationId = 0; // Starting id if no records exist
                        }

                        int newNotificationMaxId = notificationId + 1;
                        String stringNotificationMaxId = String.valueOf(newNotificationMaxId);
                        gPhoto.setPhotoid(stringNotificationMaxId);
                        gPhoto.setAlbumid(albums);
                        // Save each file (you might need to handle file type/size checks here)
                        byte[] bytes = file.getBytes();
                        gPhoto.setPhoto(bytes);
                        gPhotoRepo.save(gPhoto);
                        boolean check = gPhotoRepo.existsById(gPhoto.getPhotoid());
                        if (check) {
                            res = "success";
                        } else {
                            res = "failed";
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        return "errorPage"; // Handle error page or re-display form with error message
                    }
                }
            }




        }
        return res;
    }

    @DeleteMapping("/deletealbumphoto/{photoid}")
    @ResponseBody
    public String deleteAlbumPhoto(@PathVariable String photoid) {
        String res = "initalized";
        boolean check = gPhotoRepo.existsById(photoid);
        if (check) {
            gPhotoRepo.deleteById(photoid);
            boolean check2 = gPhotoRepo.existsById(photoid);
            if (!check2) {
                res = "success";
            } else {
                res = "failed";
            }
        }
        return res;
    }

    @GetMapping("/manageuseractiveduration")
    public String manageUserActiveDuration(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        List<?> process = processService.findProcessessListByRoleId(user.getUserrole().getRoleid());
        List<M_Processes> processList = processRepo.findAll();
        UserActiveDuration activeDuration = uActiveRepo.findById(1L).orElseThrow(() -> new EntityNotFoundException("Not found"));


        model.addAttribute("userData", user);
        model.addAttribute("processes", process);
        model.addAttribute("processlist", processList);
        model.addAttribute("activeduration", activeDuration);

        return "pages/secure/superAdmin/manageuseractiveduration";
    }

    @PostMapping("/updateactiveduration/{duration}")
    @ResponseBody
    public String updateActiveDuration(@PathVariable Integer duration) {
        System.out.println("duration " + duration);
        String res = "iniitalized";
        UserActiveDuration activeDuration = uActiveRepo.findById(1L).orElseThrow(() -> new EntityNotFoundException("Not found"));
        activeDuration.setActiveDuration(duration);
        uActiveRepo.save(activeDuration);
        res = "success";
        return res;
    }


}












