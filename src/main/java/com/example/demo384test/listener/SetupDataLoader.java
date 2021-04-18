package com.example.demo384test.listener;

import com.example.demo384test.model.Member;
import com.example.demo384test.model.Permission;
import com.example.demo384test.model.Role;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.PermissionRepository;
import com.example.demo384test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }

        Permission readPermission = createPermissionIfNotFound("READ_PERMISSION");
        Permission writePermission = createPermissionIfNotFound("WRITE_PERMISSION");
        List<Permission> adminPermissions = Arrays.asList(readPermission, writePermission);
        createRoleIfNotFound("ROLE_ADMIN", adminPermissions);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPermission));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Member member = new Member();
        member.setEmailAddress("admin@admin.com");
        member.setUsername("admin");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("admin");
        member.setPassword(encodedPassword);

        member.setName("admin");
        member.setSurname("admin");
        member.setGender("Non-disclosed");
        member.setBirthDate(new Date(Calendar.getInstance().getTime().getTime()));

        member.setRoles(Arrays.asList(adminRole));

        memberRepository.save(member);


        alreadySetup = true;

    }

    @Transactional
    Permission createPermissionIfNotFound(String name) {

        Permission permission = permissionRepository.findByName(name);
        if (permission == null) {
            permission = new Permission();
            permission.setName(name);
            permissionRepository.save(permission);
        }
        return permission;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Permission> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPermissions(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
