package com.rpsa.rpsa.service;

import com.rpsa.rpsa.model.M_Services;
import com.rpsa.rpsa.model.M_Subservices;
import com.rpsa.rpsa.repository.M_SubservicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class M_SubservicesService {

    @Autowired
    private M_SubservicesRepository m_subservicesRepository;
    public List<M_Subservices> findByServicecode(String serviceCode) {
        return m_subservicesRepository.findByServicecodeServicecode(serviceCode);
    }
}
