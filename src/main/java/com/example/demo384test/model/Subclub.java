package com.example.demo384test.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="subclubs")
public class Subclub {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;

    @ManyToOne
    @JoinColumn(name = "club_id", insertable = false, updatable = false)
    private Club club;

    @Column(nullable = false, length = 20)
    private String clubTitle;

    @OneToMany
    private Set<Member> members;

    @OneToMany
    private Set<Post> posts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public String getClubTitle() {
        return clubTitle;
    }

    public void setClubTitle(String clubTitle) {
        this.clubTitle = clubTitle;
    }
}
