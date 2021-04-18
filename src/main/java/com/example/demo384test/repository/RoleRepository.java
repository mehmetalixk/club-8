package com.example.demo384test.repository;

import com.example.demo384test.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {

}