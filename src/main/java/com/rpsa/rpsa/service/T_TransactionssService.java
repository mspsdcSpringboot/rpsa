package com.rpsa.rpsa.service;

import com.rpsa.rpsa.model.M_Action;
import com.rpsa.rpsa.model.M_Status;
import com.rpsa.rpsa.model.T_Transactionss;
import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.repository.M_ActionRepository;
import com.rpsa.rpsa.repository.M_StatusRepository;
import com.rpsa.rpsa.repository.TAppealsRepository;
import com.rpsa.rpsa.repository.T_TransactionssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service

public class T_TransactionssService {


    @Autowired
    private TAppealsRepository appealsRepository;

    @Autowired
    private M_StatusRepository statusRepo;

    @Autowired
    private M_ActionRepository actionRepo;

    @Autowired
    private T_TransactionssRepository transRepo;

    @Autowired
    private T_UserService userService;


    public T_Transactionss submitTransactions() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        T_userlogin user = userService.findByUsername(username);



        T_Transactionss trans = new T_Transactionss();

        Integer transMaxId = transRepo.findMaxUnique();
        if (transMaxId == null) {
            transMaxId = 0; // Starting id if no records exist
        }

        int newTransMaxId = transMaxId + 1;
        String stringTransMaxId = String.valueOf(newTransMaxId);



        M_Status status = statusRepo.findById("0").orElse(null);
        M_Action action = actionRepo.findById("5").orElse(null);



        trans.setTransactionscode(stringTransMaxId);
        trans.setTransactiondetails(status.getStatusname());
        trans.setAppeallevel("1");
        trans.setActioncode(action);
        trans.setTransactiondate(new Date());
        trans.setUsercode(user);
        trans.setTransactiondetails(action.getStatus());



        return trans;
    }
}
