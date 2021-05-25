package com.example.demo384test.repository;

import com.example.demo384test.model.post.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PollRepository extends JpaRepository<Poll, Long> {

    // get a poll
    @Query(value = "SELECT * FROM polls WHERE title = ?1 LIMIT 1", nativeQuery = true)
    Poll findByTitle(String title);


}
