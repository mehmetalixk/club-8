package com.example.demo384test.service;

import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.SubclubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SubclubRepository subclubRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<Subclub> listSubclubs(String keyword) {
        if (keyword != null) {
            return subclubRepository.findByTitleLike(keyword);
        }
        return subclubRepository.findAll();
    }

    public List<Member> listMember(String keyword) {
        if (keyword != null) {
            return memberRepository.findByUsernameLike(keyword);
        }
        return memberRepository.findAll();
    }
}
