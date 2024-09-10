package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.WhatsNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhatsnewRepository extends JpaRepository<WhatsNew, String> {
}
