package com.rpsa.rpsa.service;

import com.rpsa.rpsa.model.M_Designatedoffices;
import com.rpsa.rpsa.repository.M_DesignatedOfficesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class M_DesignatedOfficesService {


    @Autowired
    private M_DesignatedOfficesRepository mDesignatedofficesRepository;

    public List<Map<String, Object>> getDesignatedOfficesBySubserviceCode(String subserviceCode) {
        System.out.println("hit");
        List<Map<String, Object>> p = mDesignatedofficesRepository.getAll(subserviceCode);
        System.out.println("thfhserd"+p);
        return p;
    }


}