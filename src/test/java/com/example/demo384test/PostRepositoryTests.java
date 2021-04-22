package com.example.demo384test;

import com.example.demo384test.model.Member;
import com.example.demo384test.model.Post;
import com.example.demo384test.model.Subclub;
import com.example.demo384test.repository.ClubRepository;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.PostRepository;
import com.example.demo384test.repository.SubclubRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private SubclubRepository subclubRepository;

    @Test
    public void testCreatePost() {
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

    @Test
    public void testCreatePostWithMember() {
        Member member = new Member();
        member.setName("test");
        member.setSurname("test");
        member.setUsername("test");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("test");
        member.setPassword(encodedPassword);
        member.setEmailAddress("test");
        member.setGender("test");
        member.setBirthDate(new Date(Calendar.getInstance().getTime().getTime()));

        Member savedMember = memberRepository.save(member);

        Post post = new Post();
        post.setContent("TEST CONTENT");
        post.setTitle("TEST TITLE");
        post.setDate(LocalDate.now());
        post.setTimestamp(LocalTime.now());
        post.setMemberUsername(savedMember.getUsername());
        //post.setSubclubTitle("");

        Post savedPost = postRepository.save(post);
        Post existingPost = entityManager.find(Post.class, savedPost.getId());

        assertThat(existingPost.getTitle().equals(post.getTitle()));
        assertThat(existingPost.getMemberUsername().equals(savedMember.getUsername()));
    }

    @Test
    public void testCreatePostWithSubClub() {
        Subclub subclub = new Subclub();
        subclub.setTitle("TEST SUBCLUB");

        Subclub savedSubclub = subclubRepository.save(subclub);

        Post post = new Post();
        post.setContent("TEST CONTENT");
        post.setTitle("TEST TITLE");
        post.setDate(LocalDate.now());
        post.setTimestamp(LocalTime.now());
        //post.setMemberUsername("");
        post.setSubclubTitle(savedSubclub.getTitle());

        Post savedPost = postRepository.save(post);
        Post existingPost = entityManager.find(Post.class, savedPost.getId());

        assertThat(existingPost.getTitle().equals(post.getTitle()));
        assertThat(existingPost.getSubclubTitle().equals(savedSubclub.getTitle()));
    }
}
