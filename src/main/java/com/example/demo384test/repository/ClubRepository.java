package com.example.demo384test.repository;

import com.example.demo384test.model.Club;
import com.example.demo384test.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;


public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query(value = "SELECT * FROM clubs WHERE TITLE = ?1 LIMIT 1", nativeQuery = true)
    Club findByTitle(String title);

    @Query(value = "SELECT TITLE FROM clubs", nativeQuery = true)
    ArrayList<String> findAllTitles();
}