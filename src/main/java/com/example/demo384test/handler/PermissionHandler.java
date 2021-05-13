package com.example.demo384test.handler;

import com.example.demo384test.model.Member;
import com.example.demo384test.model.Security.Role;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.PermissionRepository;
import com.example.demo384test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;


public class PermissionHandler {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public void registerNewUserAccount(Member member) {
        for(int i= 0;i < 50; i++)
            System.out.printf("%d: %s\n",i, member.getName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        System.out.println(roleRepository.findAll());

        Role memberRole = roleRepository.findByName("ROLE_USER");
        member.setRoles(Arrays.asList(memberRole));
        memberRepository.save(member);
    }




}
