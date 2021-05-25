package com.example.demo384test.repository;

import com.example.demo384test.model.post.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Collection;

public interface EventRepository extends JpaRepository<Event, Long> {
    // get all events of a member
    @Query(value = "Select * from events e left join members m on e.member_id = m.id where m.username =?1", nativeQuery = true)
    ArrayList<Event> findAllByMemberUsername(String memberUsername);

    // get all events of a subclub
    @Query(value = "Select * from events e left join subclubs sc on e.subclub_id = sc.id left join clubs c on sc.club_id = c.id where sc.title =?1 and c.title = ?2", nativeQuery = true)
    Collection<Event> findAllBySubclubTitle(String subclubTitle, String clubTitle);

    Collection<Event> findBySubclub_members_username(String username);

    @Query(value = "SELECT * FROM events WHERE id =?1", nativeQuery = true)
    Event findByID(Long id);
}
