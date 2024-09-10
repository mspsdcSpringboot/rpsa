package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Meetings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingsRespository extends JpaRepository<Meetings, String> {
}
