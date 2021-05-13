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
    private String title;

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

}
