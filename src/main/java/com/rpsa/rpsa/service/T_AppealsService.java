package com.rpsa.rpsa.service;

import com.rpsa.rpsa.dto.AppealDTO;
import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class T_AppealsService {

    @Autowired
    private M_AppealGroundRepository appealGroundRepository;

    @Autowired
    private M_AppelateRepository appelateRepository;

    @Autowired
    private M_DesignatedOfficesRepository doRepo;

    @Autowired
    private M_SubservicesRepository ssRepo;

    @Autowired
    private T_UserService userService;

    @Autowired
    private TAppealsRepository appealsRepository;

    @Autowired
    private M_StatusRepository statusRepo;

    @Autowired
    private  M_ActionRepository actionRepo;

    @Autowired
    private T_TransactionssRepository transRepo;


    public T_Appeals submitAppeal(AppealDTO appealDto) {

        M_AppealGround aGround = appealGroundRepository.findById(appealDto.getGroundcode()).orElse(null);
        M_Appelate appelate = appelateRepository.findById(appealDto.getAppelateid()).orElse(null);
        M_Designatedoffices dOffices = doRepo.findById(appealDto.getDesignatedofficer()).orElse(null);
        M_Subservices subService = ssRepo.findById(appealDto.getSubservice()).orElse(null);

        if (aGround == null || appelate == null || dOffices == null || subService == null) {
            throw new EntityNotFoundException("One or more entities were not found");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);

        T_Appeals appeal = new T_Appeals();

        // Set the appealcode
        Integer appealMaxId = appealsRepository.findMaxId();

        System.out.println("Max appeal Id--------------------------------------" + appealMaxId);

        if (appealMaxId == null) {
            appealMaxId = 0; // Starting id if no records exist
        }

        int newAppealMaxId = appealMaxId + 1;
        String stringMaxId = String.valueOf(newAppealMaxId);
        appeal.setAppealcode(stringMaxId);


        appeal.setUsercode(user);
        appeal.setRelief1(appealDto.getRelief1());


        String applicationDate = appealDto.getApplicationdate();
        Date date = null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date = formatter.parse(applicationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        appeal.setApplicationdate(date);
        appeal.setFirstappealdate(new Date());



        // Generate reference number and update it in the new appeal object
        String referenceNumber = "MSPSDC" + "/" + LocalDate.now().getYear() + "/" + stringMaxId;
        appeal.setRefno(referenceNumber);

        M_Status status = statusRepo.findById("0").orElse(null);



        appeal.setOtherinfo1(appealDto.getOtherinfo1());
        appeal.setHearingtype(appealDto.getHearingtype());
        appeal.setGroundcode1(aGround);
        appeal.setAppelateid(appelate);
        appeal.setOfficeid(dOffices);
        appeal.setSubservicecode(subService);
        appeal.setOtherinfo2(appealDto.getOtherinfo1());
        appeal.setApplicationrefno(appealDto.getApplicationrefno());
        appeal.setAppeallevel("1");
        appeal.setPaymentflag("N");
        appeal.setPenaltyflag("0");
        appeal.setStatusid(status);
        appeal.setDaysleft("30");
        appeal.setLastactiondate(new Date());

        M_Action action = actionRepo.findById("5").orElse(null);


//        T_Transactionss trans = new T_Transactionss();
//
//        Integer transMaxId = transRepo.findMaxUnique();
//        if (transMaxId == null) {
//            transMaxId = 0; // Starting id if no records exist
//        }
//
//        int newTransMaxId = transMaxId + 1;
//        String stringTransMaxId = String.valueOf(newTransMaxId);
//
//
//        trans.setTransactionscode(stringTransMaxId);
//        trans.setAppealcode(appeal);
//        trans.setTransactiondetails(status.getStatusname());
//        trans.setAppeallevel("1");
//        trans.setActioncode(action);
//        trans.setTransactiondate(new Date());
//        trans.setUsercode(user);
//        trans.setTransactiondetails(action.getStatus());

//        transRepo.save(trans);

        return appeal;
    }
}
