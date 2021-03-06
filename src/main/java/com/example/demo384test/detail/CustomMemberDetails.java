package com.example.demo384test.detail;


import com.example.demo384test.model.Member;
import com.example.demo384test.model.Security.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class CustomMemberDetails implements UserDetails {
    private Member member;

    private Collection<? extends GrantedAuthority> roles;
    private Collection<? extends GrantedAuthority> permissions;


    public CustomMemberDetails(Member member, Collection<? extends GrantedAuthority> roles,
                               Collection<? extends GrantedAuthority> permissions) {
        this.roles = roles;
        this.permissions = permissions;
        this.member = member;
    }

    public CustomMemberDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasPermission(String check) {
        for (Role role : member.getRoles()) {
            if (check.equals(role.getName()))
                return true;
        }
        return false;
    }
    /*
    public void addPost(Post p) {
        member.addPost(p);
    }

    public void removePost(Post p) {
        member.removePost(p);
    }
    */
}
