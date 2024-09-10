package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, String> {
}
