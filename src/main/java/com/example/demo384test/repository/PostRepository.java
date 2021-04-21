package com.example.demo384test.repository;

import com.example.demo384test.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM posts WHERE TITLE = ?1 LIMIT 1", nativeQuery = true)
    Post findByTitle(String title);


}
