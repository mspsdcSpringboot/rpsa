package com.rpsa.rpsa.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.rpsa.rpsa.model.VahanOffices;
import com.rpsa.rpsa.model.VahanServiceWiseDashboard;
import com.rpsa.rpsa.model.VahanTotalDashboard;
import com.rpsa.rpsa.repository.VahanOfficesRepository;
import com.rpsa.rpsa.repository.VahanServiceWiseDashboardRepository;
import com.rpsa.rpsa.repository.VahanTotalDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VahanService {
    @Autowired
    private VahanTotalDashboardRepository dashboardRepository;

    @Autowired
    private VahanServiceWiseDashboardRepository serviceWiseDashboardRepository;

    @Autowired
    private VahanOfficesRepository officesRepository;

    // Method to save the new data
    @Transactional
    public void saveOrUpdateTotalDashboardData(String applied, String approved, String pending, String rejected) {
        // Check if the table is empty
        if (dashboardRepository.count() > 0) {
            // If not empty, delete all records
            dashboardRepository.deleteAll();
        }

        // Save the new data
        VahanTotalDashboard newDashboardData = new VahanTotalDashboard();
        newDashboardData.setSlNo(1L); // Assuming you want to set a static slNo, otherwise generate it.
        newDashboardData.setTotalApplied(applied);
        newDashboardData.setTotalApproved(approved);
        newDashboardData.setTotalPending(pending);
        newDashboardData.setTotalRejected(rejected);

        // Save the new entity into the database
        dashboardRepository.save(newDashboardData);
    }

    @Transactional
    public String saveOrUpdateServiceWiseDashboardData(JsonNode vahanArray) {

        String res = "initiated";
//        // Check if the table is empty
        if (serviceWiseDashboardRepository.count() > 0) {
            // If not empty, delete all records
            serviceWiseDashboardRepository.deleteAll();
        }

        for (JsonNode apiObject : vahanArray){
            String purCd = apiObject.get("purCd").asText();
            String purDesc = apiObject.get("purDesc").asText();
            String applied = apiObject.get("applied").asText();
            String approved = apiObject.get("approved").asText();
            String pending = apiObject.get("pending").asText();
            String rejected = apiObject.get("rejected").asText();
//            VahanOffices vahanOffice = officesRepository.findById(purCd).orElse(null);


            VahanServiceWiseDashboard serviceWiseDashboard = new VahanServiceWiseDashboard();

            serviceWiseDashboard.setServiceCode(purCd);
            serviceWiseDashboard.setServiceName(purDesc);
            serviceWiseDashboard.setServiceApplied(applied);
            serviceWiseDashboard.setServiceApproved(approved);
            serviceWiseDashboard.setServicePending(pending);
            serviceWiseDashboard.setServiceRejected(rejected);
            serviceWiseDashboardRepository.save(serviceWiseDashboard);
        }
        if (serviceWiseDashboardRepository.count() > 0) {
            res = "saved";
        }
        return res;

    }
}
