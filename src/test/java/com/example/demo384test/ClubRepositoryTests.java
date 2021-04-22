package com.example.demo384test;


import com.example.demo384test.model.Club;
import com.example.demo384test.model.Permission;
import com.example.demo384test.repository.ClubRepository;
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
public class ClubRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClubRepository clubRepository;

    @Test
    public void testCreateClub(){
        Club club = new Club();
        club.setTitle("TEST CLUB");

        Club savedClub = clubRepository.save(club);
        Club existingClub = entityManager.find(Club.class, savedClub.getId());

        assertThat(existingClub.getTitle().equals(club.getTitle()));
    }

}
