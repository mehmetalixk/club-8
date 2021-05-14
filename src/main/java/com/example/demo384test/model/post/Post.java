package com.example.demo384test.model.post;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
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

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Member.class)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable=false)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Subclub.class)
    @JoinColumn(name = "subclub_id", referencedColumnName = "id", nullable=false)
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

}
