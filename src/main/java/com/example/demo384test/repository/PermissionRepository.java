package com.example.demo384test.repository;

import com.example.demo384test.model.Security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "SELECT * FROM permissions WHERE name = ?1 LIMIT 1", nativeQuery = true)
    Permission findByName(String name);
}