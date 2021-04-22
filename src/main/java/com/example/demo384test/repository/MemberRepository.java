package com.example.demo384test.repository;

import com.example.demo384test.model.Member;
import com.example.demo384test.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT * FROM members WHERE EMAILADDRESS = ?1 LIMIT 1", nativeQuery = true)
    Member findByEmail(String email);

    @Query(value = "SELECT * FROM members WHERE USERNAME = ?1 LIMIT 1", nativeQuery = true)
    Member findByUsername(String username);


}
