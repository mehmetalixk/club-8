package com.example.demo384test.model.Club;

import com.example.demo384test.model.Member;
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
    private String title;
    private String photoPath;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Club.class)
    @JoinColumn(name = "club_id", referencedColumnName = "id", nullable=false)
    private Club club;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Member.class)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable=false)
    private Set<Member> members = new HashSet<>();

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

    public void addMemberToSubclub(Member member) {
        this.members.add(member);
    }

    public void removeMemberFromSubclub(Member member) {
        this.members.remove(member);
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoName) {
        this.photoPath = photoName;
    }
}
