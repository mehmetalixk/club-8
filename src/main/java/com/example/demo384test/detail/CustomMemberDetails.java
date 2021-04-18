package com.example.demo384test.detail;


import com.example.demo384test.model.Member;
import com.example.demo384test.model.Permission;
import com.example.demo384test.model.Role;
import jdk.jshell.Snippet;
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

    public CustomMemberDetails createUserPrincipal(Member member) {
        if (member != null) {
            List<GrantedAuthority> roles= member.getRoles().stream().filter(Objects::nonNull)
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            List<GrantedAuthority> permissions = member.getRoles().stream().filter(Objects::nonNull)
                    .map(Role::getPermissions).flatMap(Collection::stream)
                    .map(permission-> new SimpleGrantedAuthority(permission.getName()))
                    .collect(Collectors.toList());

            return CustomMemberDetails.builder()
                    .member(member)
                    .roles(roles)
                    .permissions(permissions)
                    .build();
        }
        return null;
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
}
