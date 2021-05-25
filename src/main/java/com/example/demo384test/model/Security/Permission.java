package com.example.demo384test.model.Security;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Ex: READ,WRITE,UPDATE
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Collection<Role> roles;

    public Permission(String name) {
        this.name = name;
    }

    public Permission() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
