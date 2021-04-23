package com.example.demo384test;


import com.example.demo384test.model.Club;
import com.example.demo384test.model.Subclub;
import com.example.demo384test.repository.ClubRepository;
import com.example.demo384test.repository.SubclubRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Rollback(value = true)
public class SubClubRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private SubclubRepository subclubRepository;

    @Test
    public void testCreateSubClub() {
        Subclub subclub = new Subclub();
        subclub.setTitle("TEST SUBCLUB");

        Subclub savedSubclub = subclubRepository.save(subclub);
        Subclub existingSubclub = entityManager.find(Subclub.class, savedSubclub.getId());

        assertThat(existingSubclub.getTitle(), equalTo(subclub.getTitle()));
    }

    @Test
    public void testCreateSubClubWithParentClub() {

        Club club = new Club();
        club.setTitle("TEST CLUB");

        Club savedClub = clubRepository.save(club);
        Club existingClub = entityManager.find(Club.class, savedClub.getId());

        Subclub subclub = new Subclub();
        subclub.setTitle("TEST SUBCLUB");
        subclub.setClubTitle(existingClub.getTitle());

        Subclub savedSubclub = subclubRepository.save(subclub);
        Subclub existingSubclub = entityManager.find(Subclub.class, savedSubclub.getId());

        assertThat(existingSubclub.getTitle(), equalTo(subclub.getTitle()));
        assertThat(existingSubclub.getClubTitle(), equalTo(subclub.getClubTitle()));
    }
}
