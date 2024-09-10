package com.rpsa.rpsa.service;

import com.rpsa.rpsa.model.M_Services;
import com.rpsa.rpsa.repository.M_ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service



public class M_ServicesService {

    @Autowired
    private M_ServicesRepository mServicesRepository;

    public List<M_Services> getServiceList() {
        return mServicesRepository.findAll();
    }
}
