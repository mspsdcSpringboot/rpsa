package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.InitiatedData;
import com.rpsa.rpsa.model.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InitiatedDataRepository extends JpaRepository<InitiatedData, String> {

//    @Query("SELECT "
//            + "    COUNT(*) AS totalSubmitted, "
//            + "    COUNT(CASE WHEN status = '4' THEN 1 END) AS totalDelivered, "
//            + "    COUNT(CASE WHEN status = '5' THEN 1 END) AS totalRejected, "
//            + "		COUNT(CASE WHEN status='2' THEN 1 END) AS withApplicant, "
//            + "COUNT(CASE WHEN CAST(daystaken AS INTEGER) >= 0 AND status NOT IN ('4', '5') and status in ('0','1','3') THEN 1 END) AS withOfficialWithinSLA, "
//            + "COUNT(CASE WHEN CAST(daystaken AS INTEGER) < 0 AND status NOT IN ('4', '5') and status in ('0','1','3') THEN 1 END) AS withOfficialBeyondSLA, "
//            + "    COUNT(CASE WHEN status IN ('0', '1', '2', '3') THEN 1 END) AS totalPending "
//            + "FROM "
//            + "    InitiatedData")
//
//
//    SubmissionDetailsDto getSubmissionDetails();


    }
