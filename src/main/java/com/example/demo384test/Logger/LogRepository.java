package com.example.demo384test.Logger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogRepository extends JpaRepository<Logger, Long> {


    List<Logger> findByLevel(String level);

    @Query(value = "SELECT * FROM logs WHERE id = ?1 ", nativeQuery = true)
    Logger findByID(Long id);

}
