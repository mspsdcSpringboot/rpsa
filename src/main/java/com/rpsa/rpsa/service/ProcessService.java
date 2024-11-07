package com.rpsa.rpsa.service;

import com.rpsa.rpsa.model.M_Processes;
import com.rpsa.rpsa.repository.M_processesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service

public class ProcessService {

    @Autowired
    private M_processesRepository processRepo;


    public List<?> findProcessessListByRoleId(String roleid) {
        List<?> processesData = getProcessData(findProcessessByRoleId(roleid));
        return processesData;
    }

    public List<String> findProcessessByRoleId(String roleid) {
        List<String> processId = processRepo.getProcessessByRoleId(roleid);
        return processId;
    }


    //Getting the Process Data in details
    public List<?> getProcessData(List<String> processId){
        List<String> sortedProcessIds = processId.stream()
                .map(Integer::parseInt) // Convert strings to integers
                .sorted() // Sort integers
                .map(String::valueOf) // Convert integers back to strings
                .toList();

        System.out.println("sortedProcessIds " + sortedProcessIds);

        List<M_Processes> processList = new ArrayList<M_Processes>();
        for(String process : sortedProcessIds){
            M_Processes processes = processRepo.findById(process).orElse(null);
            if(processes!= null){
                processList.add(processes);
            }
        }
        return processList;
    }
}
