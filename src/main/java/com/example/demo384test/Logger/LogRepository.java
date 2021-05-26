package com.example.demo384test.Logger;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Logger, Long> {


    List<Logger> findByLevel(String level);

}
