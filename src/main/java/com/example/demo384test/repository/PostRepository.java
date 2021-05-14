package com.example.demo384test.repository;

import com.example.demo384test.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM posts WHERE TITLE = ?1 LIMIT 1", nativeQuery = true)
    Post findByTitle(String title);

    @Query(value = "Select * from posts p left join subclubs sc on p.subclub_id = sc.id left join clubs c on sc.club_id = c.id where sc.title =?1 and c.title = ?2", nativeQuery = true)
    Collection<Post> findAllBySubclubTitle(String subclubTitle, String clubTitle);

    @Query(value = "Select * from posts p left join subclubs sc on p.subclub_id = sc.id where sc.title =?1 AND p.title = ?2", nativeQuery = true)
    Post findBySubclubTitleAndTitle(String subclubTitle, String title);


}
