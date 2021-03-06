package com.example.demo384test.model.post;


import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.*;

@Entity
@Table(name="events")
public class Event {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Member.class)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable=false)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Subclub.class)
    @JoinColumn(name = "subclub_id", referencedColumnName = "id", nullable=false)
    private Subclub subclub;

    private String location;
    private LocalDate date;
    private LocalTime time;
    private String content;


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

    public Subclub getSubclub() {
        return subclub;
    }

    public void setSubclub(Subclub subclub) {
        this.subclub = subclub;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
