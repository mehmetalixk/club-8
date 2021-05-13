package com.example.demo384test.repository;

import com.example.demo384test.model.Security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM roles WHERE name = ?1 LIMIT 1", nativeQuery = true)
    Role findByName(String name);
}