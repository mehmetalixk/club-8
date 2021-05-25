package com.example.demo384test.controller;

import com.example.demo384test.model.post.Like;
import com.example.demo384test.repository.LikeRepository;
import com.example.demo384test.request.CommentCreationRequest;
import com.example.demo384test.request.LikeCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LikeController {
    @Autowired
    private LikeRepository likeRepository;


    @GetMapping(path="/likes/all")
    public @ResponseBody
    Iterable<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    @PostMapping("/process_add_like")
    public ModelAndView processAddLike(LikeCreationRequest lcr) {
        /*
         * Create likes here
         * */
        return new ModelAndView("success");
    }
}
