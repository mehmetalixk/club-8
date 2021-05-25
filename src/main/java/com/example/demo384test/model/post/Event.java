package com.example.demo384test.model.post;


import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import org.springframework.data.annotation.Id;
import java.sql.Date;
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
    private Date date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
