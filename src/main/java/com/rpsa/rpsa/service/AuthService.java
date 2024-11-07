package com.rpsa.rpsa.service;

import com.rpsa.rpsa.dto.DoSubOrdinateDTO;
import com.rpsa.rpsa.dto.LoginUserDto;
import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.repository.T_userloginRepository;
import com.rpsa.rpsa.repository.UserActiveDurationRepository;
import com.rpsa.rpsa.repository.UserRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {


    @Autowired
    private T_userloginRepository t_userloginRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserActiveDurationRepository uActiveRepo;

    @Autowired
    private T_UserService userService;


    public String registerUser(T_userlogin user) {

        String res = "-1";

        System.out.println("Payload######################## " + user);


        T_userlogin registerUser = new T_userlogin();

        Integer userMaxId = t_userloginRepository.findMaxId();
        if (userMaxId == null) {
            userMaxId = 0; // Starting id if no records exist
        }

        int newUserId = userMaxId + 1;



        //User role object
        UserRole userRole = new UserRole();
        District userDistrict = new District();
        M_department userDepartment = new M_department();
        M_Designatedoffices userDesignatedoffices = new M_Designatedoffices();
        M_Appelate userAppelate = new M_Appelate();



        if(user.getUserrole() == null){
            Integer roleMaxId = userRoleRepository.findMaxId();
            if (roleMaxId == null) {
                roleMaxId = 0; // Starting id if no records exist
            }

            int newRoleId = roleMaxId + 1;
            userRole.setRoleid("0");
            userRole.setRolename("USER");
        }

        else{
            Integer roleMaxId = userRoleRepository.findMaxId();
            if (roleMaxId == null) {
                roleMaxId = 0; // Starting id if no records exist
            }

            int newRoleId = roleMaxId + 1;

            userRole.setRoleid(String.valueOf(newRoleId));
            userRole.setRolename(user.getUserrole() == null ? "USER" : user.getUserrole().getRolename());
        }



        if (user.getDistrictcode() != null) {
            //User district object

            userDistrict.setDistrictcode(user.getDistrictcode().getDistrictcode());
            userDistrict.setDistrictname(user.getDistrictcode().getDistrictname());
            userDistrict.setShortname(user.getDistrictcode().getShortname());

            //User state object
            M_state userState = new M_state();
            userState.setStatecode(user.getDistrictcode().getStates().getStatecode());
            userState.setStatecode(user.getDistrictcode().getStates().getStatename());

            //Setting user state to user district
            userDistrict.setStates(userState);
        }
        else{
            userDistrict = null;
        }

        if (user.getDepartmentcode() != null){

            userDepartment.setDepartmentcode(user.getDepartmentcode().getDepartmentcode());
            userDepartment.setDepartmentname(user.getDepartmentcode().getDepartmentname());
        }
        else{
            userDepartment = null;
        }

        if(user.getOfficeid() != null){

            userDesignatedoffices.setOfficeid(user.getOfficeid().getOfficeid());
            userDesignatedoffices.setOfficename(user.getOfficeid().getOfficename());
            userDesignatedoffices.setOfficername(user.getOfficeid().getOfficername());
            userDesignatedoffices.setEmail(user.getOfficeid().getEmail());
            userDesignatedoffices.setMobile(user.getOfficeid().getMobile());
            userDesignatedoffices.setCreated(user.getOfficeid().getCreated());
            userDesignatedoffices.setUsername(user.getOfficeid().getUsername());
            userDesignatedoffices.setSubmission_location_id(user.getOfficeid().getSubmission_location_id());
        }
        else{
           userDesignatedoffices = null;
        }

        if(user.getAppelateid() != null){
            userAppelate.setAppelateid(user.getAppelateid().getAppelateid());
            userAppelate.setOfficename(user.getAppelateid().getOfficename());
            userAppelate.setOfficelevel(user.getAppelateid().getOfficelevel());
            userAppelate.setSubmission_location_id(user.getAppelateid().getSubmission_location_id());
        }
        else{
            userAppelate = null;
        }

        UserActiveDuration activeDur = uActiveRepo.findById(1L).orElseThrow(() -> new EntityNotFoundException("Not found"));




        registerUser.setUsercode(String.valueOf(newUserId));
        registerUser.setFullname(user.getFullname());
        registerUser.setMname(user.getMname());
        registerUser.setLname(user.getLname());
        registerUser.setUsername(user.getContact());
        registerUser.setEmail(user.getEmail());
        registerUser.setUserpassword(user.getUserpassword());
        registerUser.setContact(user.getContact() == null ? user.getUsername() : user.getContact());
        registerUser.setDesignation(user.getDesignation());
        registerUser.setActive(user.getActive());
        registerUser.setUserrole(userRole);
        registerUser.setDistrictcode(userDistrict);
        registerUser.setDepartmentcode(userDepartment);
        registerUser.setOfficeid(userDesignatedoffices);
        registerUser.setActiveDays(0);
        registerUser.setUserActiveDuration(activeDur);
        registerUser.setAddress(user.getAddress());

        T_userlogin checkContact = t_userloginRepository.findUserByContact(user.getContact());
        System.out.println("##############Contact Check#############" + checkContact);


        if (checkContact != null) {
            res = "-2";
        } else {
            t_userloginRepository.save(registerUser);
            res = "1";
        }

        System.out.println("############Response################" + res);
        return res;

    }

    public List<T_userlogin> getUserList() {
        return t_userloginRepository.findAll();
    }

    public String loginUser(LoginUserDto loginUserDto) {
        String res = "1";
        return res;
    }

    public String createDoSub(DoSubOrdinateDTO doSubDto) {

        String res = "initiated";
        Integer userMaxId = t_userloginRepository.findMaxId();
        if (userMaxId == null) {
            userMaxId = 0; // Starting id if no records exist
        }

        int newUserId = userMaxId + 1;



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        T_userlogin userSo = new T_userlogin();
        userSo.setUsercode(String.valueOf(newUserId));
        userSo.setFullname(doSubDto.getFullname());
        userSo.setEmail(doSubDto.getEmail());
        userSo.setDesignation(doSubDto.getDesignation());
        userSo.setUsername(doSubDto.getEmail());
        userSo.setUserpassword(doSubDto.getPw());
        userSo.setActive("Y");
        M_Designatedoffices d = new M_Designatedoffices();
        d.setOfficeid(user.getOfficeid().getOfficeid());
        userSo.setOfficeid(d);
        userSo.setContact(doSubDto.getContact());
        UserRole role = new UserRole();
        role.setRoleid("10");
        userSo.setUserrole(role);
        UserActiveDuration activeDur = uActiveRepo.findById(1L).orElseThrow(() -> new EntityNotFoundException("Not found"));
        userSo.setActiveDays(0);
        userSo.setUserActiveDuration(activeDur);
        System.out.println("...............DO SUB ORDINATE PASSWORD....................  " + doSubDto.getPassword());
        t_userloginRepository.save(userSo);

        boolean check = t_userloginRepository.existsById(userSo.getUsercode());

        if(check){
            res = "created";
        }else{
            res = "failed";
        }
        return res;
    }

    public String createAaSub(DoSubOrdinateDTO doSubDto) {

        String res = "initiated";
        Integer userMaxId = t_userloginRepository.findMaxId();
        if (userMaxId == null) {
            userMaxId = 0; // Starting id if no records exist
        }

        int newUserId = userMaxId + 1;



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);
        T_userlogin userSo = new T_userlogin();
        userSo.setUsercode(String.valueOf(newUserId));
        userSo.setFullname(doSubDto.getFullname());
        userSo.setEmail(doSubDto.getEmail());
        userSo.setDesignation(doSubDto.getDesignation());
        userSo.setUsername(doSubDto.getEmail());
        userSo.setUserpassword(doSubDto.getPw());
        userSo.setActive("Y");
        M_Appelate a = new M_Appelate();
        a.setAppelateid(user.getAppelateid().getAppelateid());
        userSo.setAppelateid(a);
        userSo.setContact(doSubDto.getContact());
        UserRole role = new UserRole();
        role.setRoleid("6");
        userSo.setUserrole(role);
        UserActiveDuration activeDur = uActiveRepo.findById(1L).orElseThrow(() -> new EntityNotFoundException("Not found"));
        userSo.setActiveDays(0);
        userSo.setUserActiveDuration(activeDur);
        t_userloginRepository.save(userSo);
        System.out.println("..........Password........ - " + doSubDto.getPassword());

        boolean check = t_userloginRepository.existsById(userSo.getUsercode());

        if(check){
            res = "created";
        }else{
            res = "failed";
        }
        return res;
    }
}
