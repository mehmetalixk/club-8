package com.example.demo384test;


import com.example.demo384test.model.Permission;
import com.example.demo384test.model.Role;
import com.example.demo384test.repository.PermissionRepository;
import com.example.demo384test.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Rollback(value = true)
public class RoleRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateRole() {
        Role role = new Role();
        role.setName("ROLE_TEST");

        Permission permission = new Permission();
        permission.setName("TEST_PERMISSION");

        Permission savedPermission = permissionRepository.save(permission);
        role.setPermissions(Arrays.asList(savedPermission));

        Role savedRole = roleRepository.save(role);
        Role existingRole = entityManager.find(Role.class, savedRole.getId());

        assertThat(existingRole.getName(), equalTo(role.getName()));
    }

    @Test
    public void testCreateRoleWithMultiplePermissions() {
        Role role = new Role();
        role.setName("ROLE_ANOTHERTEST");

        Permission permission1 = new Permission();
        Permission permission2 = new Permission();
        Permission permission3 = new Permission();
        permission1.setName("TEST1_PERMISSION");
        permission2.setName("TEST2_PERMISSION");
        permission3.setName("TEST3_PERMISSION");

        Permission savedPermission1 = permissionRepository.save(permission1);
        Permission savedPermission2 = permissionRepository.save(permission2);
        Permission savedPermission3 = permissionRepository.save(permission3);

        role.setPermissions(Arrays.asList(savedPermission1, savedPermission2, savedPermission3));
        Role savedRole = roleRepository.save(role);
        Role existingRole = entityManager.find(Role.class, savedRole.getId());

        assertThat(existingRole.getName(), equalTo(role.getName()));
        assertThat(existingRole.getPermissions(), containsInAnyOrder(role.getPermissions().toArray()));
    }
}
