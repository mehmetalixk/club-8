package com.example.demo384test;


import com.example.demo384test.model.Permission;
import com.example.demo384test.repository.PermissionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Rollback(value = true)
public class PermissionRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    public void testCreatePermission(){
        Permission permission = new Permission();
        permission.setName("TEST_PERMISSION");

        Permission savedPermission = permissionRepository.save(permission);
        Permission existingPermission = entityManager.find(Permission.class, savedPermission.getId());

        assertThat(existingPermission.getName().equals(permission.getName()));

    }
}
