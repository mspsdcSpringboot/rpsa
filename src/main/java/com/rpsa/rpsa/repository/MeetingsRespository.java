package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Meetings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingsRespository extends JpaRepository<Meetings, String> {

    @Query("SELECT CAST(MAX(CAST(m.meetingid AS int)) AS int) FROM Meetings m")
    Integer findMaxId();
}
