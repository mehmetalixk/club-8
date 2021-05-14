package com.example.demo384test.repository;

import com.example.demo384test.model.post.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // get all likes of a member
    @Query(value = "Select * from likes l left join members m on l.member_id = m.id where m.username =?1", nativeQuery = true)
    ArrayList<Like> findAllByMemberUsername(String memberUsername);

    // get all likes of a post
    @Query(value = "Select * from likes l left join posts p on l.post_id = p.id where p.title =?1", nativeQuery = true)
    ArrayList<Like> findAllByPostTitle(String postTitle);

    // get all comments of a post by a specific member
    @Query(value = "SELECT * FROM likes l JOIN posts p ON l.post_id = p.id JOIN members m ON l.member_id = m.id WHERE p.title = ?1 AND m.username = ?2", nativeQuery = true)
    Like findByPostTitleAndMember(String postTitle, String memberUsername);

}
