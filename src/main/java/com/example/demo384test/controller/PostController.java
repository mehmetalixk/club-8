package com.example.demo384test.controller;


import com.example.demo384test.model.Post;
import com.example.demo384test.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
