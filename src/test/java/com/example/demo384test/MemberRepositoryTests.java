package com.example.demo384test;

import com.example.demo384test.model.Member;
import com.example.demo384test.model.Permission;
import com.example.demo384test.model.Role;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.PermissionRepository;
import com.example.demo384test.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateMember() {
        Member member = new Member();
        member.setName("test");
        member.setSurname("test");
        member.setUsername("test");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("test");
        member.setPassword(encodedPassword);
        member.setEmailAddress("test");
        member.setGender("test");
        member.setBirthDate(new Date(Calendar.getInstance().getTime().getTime()));

        Member savedMember = memberRepository.save(member);
        Member existingMember = entityManager.find(Member.class, savedMember.getId());

        assertThat(existingMember.getEmailAddress(), equalTo(member.getEmailAddress()));
        assertThat(existingMember.getUsername(), equalTo(member.getUsername()));
        assertThat(existingMember.getName(), equalTo(member.getName()));
        assertThat(existingMember.getSurname(), equalTo(member.getSurname()));
    }

    @Test
    public void testRoleAssignMember() {
        Member member = new Member();
        member.setName("test");
        member.setSurname("test");
        member.setUsername("test");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("test");
        member.setPassword(encodedPassword);
        member.setEmailAddress("test");
        member.setGender("test");
        member.setBirthDate(new Date(Calendar.getInstance().getTime().getTime()));

        Role role = new Role();
        role.setName("ROLE_TEST");

        Permission permission = new Permission();
        permission.setName("TEST_PERMISSION");

        Permission savedPermission = permissionRepository.save(permission);
        role.setPermissions(Arrays.asList(savedPermission));

        Role savedRole = roleRepository.save(role);

        member.setRoles(Arrays.asList(savedRole));

        Member savedMember = memberRepository.save(member);
        Member existingMember = entityManager.find(Member.class, savedMember.getId());
        assertThat(existingMember.getRoles(), is(equalTo(member.getRoles())));
    }
}
