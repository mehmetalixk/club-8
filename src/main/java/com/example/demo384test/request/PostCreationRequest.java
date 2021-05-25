package com.example.demo384test.request;

import org.springframework.web.multipart.MultipartFile;

public class PostCreationRequest {

    private String clubTitle;
    private String subclubTitle;
    private String title;
    private String content;
    private MultipartFile postImage;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MultipartFile getPostImage() {
        return postImage;
    }

    public void setPostImage(MultipartFile postImage) {
        this.postImage = postImage;
    }
}
