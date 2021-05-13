package com.example.demo384test.model.Club;

import com.example.demo384test.model.Post;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.HashSet;
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


    private String clubTitle;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "subclub_posts",
            joinColumns = @JoinColumn(
                    name = "subclub_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "post_id", referencedColumnName = "id"))
    private Set<Post> posts = new HashSet<>();


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

    public void addPostToSubclub(Post p) {
        posts.add(p);
    }

    public void removePostFromSubclub(Post p) {
        posts.remove(p);
    }


}
