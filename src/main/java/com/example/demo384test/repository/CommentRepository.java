package com.example.demo384test.repository;

import com.example.demo384test.model.Club.Comment;
import com.example.demo384test.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // get all comments of a member
    @Query(value = "Select * from comments c left join members m on c.member_id = m.id where m.username =?1", nativeQuery = true)
    Collection<Post> findAllByMemberUsername(String memberUsername);

    // get all comments of a post
    @Query(value = "Select * from comments c left join posts p on c.post_id = p.id where p.title =?1", nativeQuery = true)
    Collection<Post> findAllByPostTitle(String postTitle);

}
