package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.servlet.function.RouterFunctions;


public interface VisitorsRepository extends JpaRepository<Visitors, String>{

}

