package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.TransportDataDump;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportDataDumpRepository extends JpaRepository<TransportDataDump, Long> {
    @Query("SELECT CAST(MAX(CAST(m.slno AS int)) AS int) FROM TransportDataDump m")
    Integer findMaxId();

    List<TransportDataDump> findAllByServicename(String issueOfLearnersLicence);

    @Query(value = "SELECT " +
            "servicename, " +
            "SUM(CAST(delivered AS INT)) AS total_delivered, " +
            "SUM(CAST(applied AS INT)) AS total_applied, " +
            "SUM(CAST(pending AS INT)) AS total_pending, " +
            "SUM(CAST(rejected AS INT)) AS total_rejected " +
            "FROM rpsa.transportdatadump " +
            "WHERE submissionlocationid = :submissionlocationid " +
            "GROUP BY servicename",
            nativeQuery = true)
    List<Object[]> getServiceWiseSummary(@Param("submissionlocationid") String submissionlocationid);



    @Query(value = "SELECT " +
            "SUM(CAST(delivered AS INT)) AS total_delivered, " +
            "SUM(CAST(applied AS INT)) AS total_applied, " +
            "SUM(CAST(pending AS INT)) AS total_pending, " +
            "SUM(CAST(rejected AS INT)) AS total_rejected " +
            "FROM rpsa.transportdatadump " +
            "WHERE submissionlocationid = :submissionlocationid",
            nativeQuery = true)
    List<Object[]> getSummedDataBySubmissionLocation(@Param("submissionlocationid") String submissionlocationid);

}
