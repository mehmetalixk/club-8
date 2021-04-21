package com.example.demo384test.repository;

import com.example.demo384test.model.Subclub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;


public interface SubclubRepository extends JpaRepository<Subclub, Long> {

    @Query(value = "SELECT * FROM subclubs WHERE club = ?1", nativeQuery = true)
    Subclub findByClub(String club);

    @Query(value = "SELECT title FROM subclubs", nativeQuery = true)
    Collection<String> findAllTitles();

    @Query(value = "SELECT * FROM subclubs where title = ?1", nativeQuery = true)
    Subclub findByTitle(String title);
}