package com.example.demo384test.request;

import org.springframework.web.multipart.MultipartFile;

public class SubclubCreationRequest {

    private String clubTitle;
    private String title;
    private MultipartFile subclubImage;

    public String getClubTitle() {
        return clubTitle;
    }

    public void setClubTitle(String clubTitle) {
        this.clubTitle = clubTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getSubclubImage() {
        return subclubImage;
    }

    public void setSubclubImage(MultipartFile subclubImage) {
        this.subclubImage = subclubImage;
    }
}
