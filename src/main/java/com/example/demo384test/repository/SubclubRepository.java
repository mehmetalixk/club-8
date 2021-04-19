package com.example.demo384test.repository;

import com.example.demo384test.model.Subclub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SubclubRepository extends JpaRepository<Subclub, Long> {

}