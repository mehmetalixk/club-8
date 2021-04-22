package com.example.demo384test;

import com.example.demo384test.model.Permission;
import com.example.demo384test.model.Post;
import com.example.demo384test.repository.PermissionRepository;
import com.example.demo384test.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Rollback(value = true)
public class PostRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testCreatePost(){
        Post post = new Post();
        post.setContent("TEST CONTENT");
        post.setTitle("TEST TITLE");
        post.setDate(LocalDate.now());
        post.setTimestamp(LocalTime.now());
        //post.setMemberUsername("");
        //post.setSubclubTitle("");

        Post savedPost = postRepository.save(post);
        Post existingPost = entityManager.find(Post.class, savedPost.getId());

        assertThat(existingPost.getTitle().equals(post.getTitle()));
    }
}
