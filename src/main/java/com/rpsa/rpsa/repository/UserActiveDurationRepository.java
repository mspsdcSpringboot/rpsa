package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.UserActiveDuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActiveDurationRepository extends JpaRepository<UserActiveDuration, Long> {
}
