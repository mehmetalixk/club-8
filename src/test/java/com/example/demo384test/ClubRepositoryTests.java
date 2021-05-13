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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Rollback(value = true)
public class ClubRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private SubclubRepository subclubRepository;

    @Test
    public void testCreateClub() {
        Club club = new Club();
        club.setTitle("TEST CLUB");

        Club savedClub = clubRepository.save(club);
        Club existingClub = entityManager.find(Club.class, savedClub.getId());

        assertThat(existingClub.getTitle(), equalTo(club.getTitle()));
    }

    @Test
    public void testCreateClubWithSubclubs(){
        Subclub subclub1 = new Subclub();
        subclub1.setTitle("TEST SUBCLUB 1");
        Subclub subclub2 = new Subclub();
        subclub2.setTitle("TEST SUBCLUB 2");
        Subclub subclub3 = new Subclub();
        subclub3.setTitle("TEST SUBCLUB 3");
        Subclub subclub4 = new Subclub();
        subclub4.setTitle("TEST SUBCLUB 4");

        Subclub savedSubclub1 = subclubRepository.save(subclub1);
        Subclub savedSubclub2 = subclubRepository.save(subclub2);
        Subclub savedSubclub3 = subclubRepository.save(subclub3);
        Subclub savedSubclub4 = subclubRepository.save(subclub4);

        Club club = new Club();
        club.setTitle("TEST CLUB");
        club.addSubclubToClub(savedSubclub1);
        club.addSubclubToClub(savedSubclub2);
        club.addSubclubToClub(savedSubclub3);
        club.addSubclubToClub(savedSubclub4);

        Club savedClub = clubRepository.save(club);
        Club existingClub = entityManager.find(Club.class, savedClub.getId());

        assertThat(existingClub.getTitle(), equalTo(club.getTitle()));
        assertThat(existingClub.getSubclubs(), containsInAnyOrder(club.getSubclubs().toArray()));
    }

}
