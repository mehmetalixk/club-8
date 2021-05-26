package com.example.demo384test.repository;

import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Set;


public interface SubclubRepository extends JpaRepository<Subclub, Long> {

    @Query(value = "SELECT title FROM subclubs", nativeQuery = true)
    Collection<String> findAllTitles();

    @Query(value = "SELECT * FROM subclubs where title = ?1", nativeQuery = true)
    Subclub findByTitle(String title);

    @Query(value = "Select * from subclubs sc left join clubs c on sc.club_id = c.id where c.title =?1", nativeQuery = true)
    Collection<Subclub> findAllByClubTitle(String clubTitle);

    @Query(value = "SELECT * FROM subclubs sc LEFT JOIN clubs c ON sc.club_id = c.id WHERE sc.title = ?1 AND c.title = ?2", nativeQuery = true)
    Subclub findByClubTitle(String title, String clubTitle);

    @Query(value = "SELECT * FROM subclubs WHERE id = ?1", nativeQuery = true)
    Subclub findByID(Long id);

    Collection<Subclub> findByMembers_username(String username);


}
