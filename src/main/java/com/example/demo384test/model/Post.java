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

    private String subclubTitle;
    private String memberUsername;

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

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public String getMemberUsername() {
        return this.memberUsername;
    }
}
