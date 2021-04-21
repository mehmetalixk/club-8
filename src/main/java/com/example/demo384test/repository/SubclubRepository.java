package com.example.demo384test.repository;

import com.example.demo384test.model.Role;
import com.example.demo384test.model.Subclub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SubclubRepository extends JpaRepository<Subclub, Long> {

    @Query(value = "SELECT * FROM subclubs WHERE club = ?1", nativeQuery = true)
    Subclub findByClub(String club);

}