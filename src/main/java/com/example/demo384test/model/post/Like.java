package com.example.demo384test.model.post;

import com.example.demo384test.model.Member;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name="likes")
public class Like {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Member.class)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable=false)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Post.class)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable=false)
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
