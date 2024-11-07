package com.rpsa.rpsa.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.rpsa.rpsa.model.RtoDistrict;
import com.rpsa.rpsa.model.TransportDataDump;
import com.rpsa.rpsa.model.VahanServiceWiseDashboard;
import com.rpsa.rpsa.repository.RtoDistrictRepository;
import com.rpsa.rpsa.repository.TransportDataDumpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransportService {

    @Autowired
    private TransportDataDumpRepository transportRepo;

    @Autowired
    private RtoDistrictRepository rtoRepo;

    @Transactional
    public String saveOrUpdateTransportData(JsonNode transportArray) {

        String res = "initiated";
        // Check if the table is empty
        if (transportRepo.count() > 0) {
            // If not empty, delete all records
            transportRepo.deleteAll();
        }

        for (JsonNode apiObject : transportArray){
            String applType = apiObject.get("appl_type").asText();
            String serviceName = apiObject.get("service_name").asText();
            String rtoCd = apiObject.get("rto_cd").asText();
            String applied = apiObject.get("total_received_count").asText();
            String approved = apiObject.get("total_approved_count").asText();
            String pending = apiObject.get("total_pending_count").asText();
            String rejected = apiObject.get("total_rejected_count").asText();


            TransportDataDump transportData = new TransportDataDump();
            Integer appealMaxId = transportRepo.findMaxId();

            if (appealMaxId == null) {
                appealMaxId = 0; // Starting id if no records exist
            }

            int newAppealMaxId = appealMaxId + 1;
            String stringMaxId = String.valueOf(newAppealMaxId);
            transportData.setSlno(stringMaxId);
            transportData.setAppl_type(applType);
//            RtoDistrict district = rtoRepo.findById(rtoCd).orElse(null);
            transportData.setSubmissionlocationid(rtoCd);
            transportData.setServicename(serviceName);
            transportData.setApplied(applied);
            transportData.setDelivered(approved);
            transportData.setPending(pending);
            transportData.setRejected(rejected);

            transportRepo.save(transportData);
        }
        if (transportRepo.count() > 0) {
            res = "saved";
        }else {
            res="failed";
        }
        return res;

    }
}
