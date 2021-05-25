package com.example.demo384test.repository;

import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT * FROM members WHERE EMAILADDRESS = ?1 LIMIT 1", nativeQuery = true)
    Member findByEmail(String email);

    @Query(value = "SELECT * FROM members WHERE USERNAME = ?1 LIMIT 1", nativeQuery = true)
    Member findByUsername(String username);

    List<Member> findByUsernameLike(String username);


}
