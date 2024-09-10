package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Holidays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface M_HolidaysRepository extends JpaRepository<M_Holidays, Date> {
}
