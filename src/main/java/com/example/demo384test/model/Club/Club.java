package com.example.demo384test.model.Club;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="clubs")
public class Club {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "club_subclubs",
            joinColumns = @JoinColumn(
                    name = "club_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "subclub_id", referencedColumnName = "id"))
    private Set<Subclub> subclubs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addSubclubToClub(Subclub sc) {
        subclubs.add(sc);
    }

    public void removeSubclubFromClub(Subclub sc) {
        subclubs.remove(sc);
    }

    public Set<Subclub> getSubclubs() {
        return subclubs;
    }
}
