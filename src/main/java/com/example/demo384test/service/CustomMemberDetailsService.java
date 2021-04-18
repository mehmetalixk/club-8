package com.example.demo384test.service;

import com.example.demo384test.detail.CustomMemberDetails;
import com.example.demo384test.model.Member;
import com.example.demo384test.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomMemberDetailsService implements UserDetailsService {

    @Autowired
    private CustomMemberDetailsService customMemberDetailsService;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Member member = memberRepository.findByUsername(username);
        System.out.println(member);
        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomMemberDetails(member);
    }

}
