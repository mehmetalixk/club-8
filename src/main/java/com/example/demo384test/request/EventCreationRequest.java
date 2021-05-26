package com.example.demo384test.request;

import com.example.demo384test.model.post.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventCreationRequest {

    private String clubTitle;
    private String subclubTitle;
    private String content;
    private String dateTime;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private Event event;


    public String getClubTitle() {
        return clubTitle;
    }

    public void setClubTitle(String clubTitle) {
        this.clubTitle = clubTitle;
    }

    public String getSubclubTitle() {
        return subclubTitle;
    }

    public void setSubclubTitle(String subclubTitle) {
        this.subclubTitle = subclubTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
