package com.example.demo384test.listener;

import com.example.demo384test.model.Club.Club;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.Security.Permission;
import com.example.demo384test.model.Security.Role;
import com.example.demo384test.repository.*;
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

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private SubclubRepository subclubRepository;

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

        createClubIfNotFound("books");
        createClubIfNotFound("movies");
        createClubIfNotFound("sports");
        createClubIfNotFound("games");

        Subclub horror_books = createSubclubIfNotFound("horror", "books");
        createSubclubIfNotFound("drama", "books");
        createSubclubIfNotFound("sci-fi", "books");
        createSubclubIfNotFound("fantastic", "books");
        createSubclubIfNotFound("engineering", "books");

        createSubclubIfNotFound("horror", "movies");
        createSubclubIfNotFound("sci-fi", "movies");
        createSubclubIfNotFound("drama", "movies");
        createSubclubIfNotFound("fantastic", "movies");
        createSubclubIfNotFound("documentary", "movies");

        createSubclubIfNotFound("football", "sports");
        createSubclubIfNotFound("basketball", "sports");
        createSubclubIfNotFound("tennis", "sports");
        createSubclubIfNotFound("badminton", "sports");
        createSubclubIfNotFound("volleyball", "sports");

        createSubclubIfNotFound("rpg", "games");
        createSubclubIfNotFound("fps", "games");
        createSubclubIfNotFound("horror", "games");
        createSubclubIfNotFound("sci-fi", "games");

        Member admin = createAdminIfNotFound();
        horror_books.addMemberToSubclub(admin);


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
    Role createRoleIfNotFound(String name, Collection<Permission> permissions) {
        Role role = roleRepository.findByName(name);

        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPermissions(permissions);
            roleRepository.save(role);
        }

        return role;
    }

    @Transactional
    Member createAdminIfNotFound() {
        Member member = memberRepository.findByUsername("admin");

        if(member == null) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            member = new Member();
            member.setEmailAddress("admin@club8.com");
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
        }

        return member;
    }

    @Transactional
    Club createClubIfNotFound(String title) {
        Club club = clubRepository.findByTitle(title);

        // create club if not exists in the repository
        if (club == null) {
            club = new Club();
            club.setTitle(title);

            clubRepository.save(club);
        }

        return club;
    }

    @Transactional
    Subclub createSubclubIfNotFound(String title, String clubTitle) {
        Club club = clubRepository.findByTitle(clubTitle);
        // club does not exist
        if(club == null)
            return null;

        Subclub subclub = subclubRepository.findByClubTitle(title, clubTitle);
        // create subclub if not exists
        if (subclub == null) {
            subclub = new Subclub();
            subclub.setTitle(title);
            subclub.setClub(club);

            clubRepository.save(club);
            subclubRepository.save(subclub);
        }

        return subclub;

    }
}
