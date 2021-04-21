package com.example.demo384test.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="posts")
public class Post {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime timestamp;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String title;

    @OneToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    private String subclubTitle;

    @ManyToOne
    @JoinColumn(name = "subclub_id", insertable = false, updatable = false)
    private Subclub subclub;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Subclub getSubclub() {
        return subclub;
    }

    public void setSubclub(Subclub subclub) {
        this.subclub = subclub;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubclubTitle() {
        return subclubTitle;
    }

    public void setSubclubTitle(String subclubTitle) {
        this.subclubTitle = subclubTitle;
    }

    public String getMemberUsername() {
        return member.getUsername();
    }
}
