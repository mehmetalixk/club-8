package com.example.demo384test.model.Club;

import org.springframework.data.annotation.Id;
import javax.persistence.*;


@Entity
@Table(name="subclubs")
public class Subclub {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Club.class)
    @JoinColumn(name = "club_id", referencedColumnName = "id", nullable=false)
    private Club club;

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
}
