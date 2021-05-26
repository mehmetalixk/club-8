package com.example.demo384test.request;

import org.springframework.web.multipart.MultipartFile;

public class SubclubEditionRequest {

    private String id;
    private String title;
    private MultipartFile subclubImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
