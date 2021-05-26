package com.example.demo384test.request;

import com.example.demo384test.model.post.Post;

public class HomePostRequest {

    private int comments;
    private int likes;
    private Post post;


    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
