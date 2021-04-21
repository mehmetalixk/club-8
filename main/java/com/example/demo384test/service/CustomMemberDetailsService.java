package com.example.demo384test.service;

import com.example.demo384test.detail.CustomMemberDetails;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.Permission;
import com.example.demo384test.model.Role;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public String checkDuplicate(String username, String emailAddress) throws UsernameNotFoundException {
        try{
           String a = memberRepository.findByUsername(username).getUsername();
            String message = "Username exists!";
            throw new UsernameNotFoundException("Error occurred: " + message);
        }catch(NullPointerException ex0){
            try{
                String b = memberRepository.findByEmail(emailAddress).getUsername();
                String message = "Email exists!";
                throw new UsernameNotFoundException("Error occurred: " + message);
            }catch(NullPointerException ex1){
                String message = "Created your account successfully";
                return message;
            }
        }
    }
}
