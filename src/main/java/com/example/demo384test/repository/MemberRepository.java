package com.example.demo384test.repository;

import com.example.demo384test.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT * FROM members WHERE EMAILADDRESS = ?1 LIMIT 1", nativeQuery = true)
    Member findByEmail(String email);

    @Query(value = "SELECT * FROM members WHERE USERNAME = ?1 LIMIT 1", nativeQuery = true)
    Member findByUsername(String username);

    @Query(value = "SELECT * FROM members WHERE id = ?1", nativeQuery = true)
    Member findByID(Long id);
}
