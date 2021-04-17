package com.example.demo384test.repository;

import com.example.demo384test.model.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Integer> {

}
