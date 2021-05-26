package com.example.demo384test.repository;

import com.example.demo384test.model.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "Select * from comments", nativeQuery = true)
    ArrayList<Comment> findAll();

    // get all comments of a member
    @Query(value = "Select * from comments c left join members m on c.member_id = m.id where m.username =?1", nativeQuery = true)
    ArrayList<Comment> findAllByMemberUsername(String memberUsername);

    // get all comments of a post
    @Query(value = "Select * from comments c left join posts p on c.post_id = p.id where p.title =?1", nativeQuery = true)
    ArrayList<Comment> findAllByPostTitle(String postTitle);

    // get all comments of a post by a specific member
    @Query(value = "SELECT * FROM comments c JOIN posts p ON c.post_id = p.id JOIN members m ON c.member_id = m.id WHERE p.title = ?1 AND m.username = ?2", nativeQuery = true)
    ArrayList<Comment> findAllByPostTitleAndMember(String postTitle, String memberUsername);

    // get all comments of a post using post id
    @Query(value = "SELECT * FROM comments c JOIN posts p ON c.post_id = p.id WHERE p.id = ?1", nativeQuery = true)
    ArrayList<Comment> findByPostID(Long postID);


    @Query(value = "SELECT * FROM comments WHERE id = ?1", nativeQuery = true)
    Comment findByID(Long commentID);

}
