package com.example.demo384test.service;

import com.example.demo384test.detail.CustomMemberDetails;
import com.example.demo384test.model.Member;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomMemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MessageSource messages;

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
