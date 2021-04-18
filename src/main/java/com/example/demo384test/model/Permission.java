package com.example.demo384test.model;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {

    @javax.persistence.Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    // Ex: READ,WRITE,UPDATE
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
