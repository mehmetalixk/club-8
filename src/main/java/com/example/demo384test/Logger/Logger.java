package com.example.demo384test.Logger;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="logs")
public class Logger {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String level;
    @Column(nullable = false)
    private String content;
    private LocalTime timestamp;
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
