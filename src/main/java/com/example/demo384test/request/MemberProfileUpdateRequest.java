package com.example.demo384test.request;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

public class MemberProfileUpdateRequest {
    private String password;
    private String name;
    private String surname;
    private MultipartFile pp;
    private String emailAddress;
    private Date birthDate;
    private String id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public MultipartFile getPp() {
        return pp;
    }

    public void setPp(MultipartFile pp) {
        this.pp = pp;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
