package com.example.demo384test.controller;

import com.example.demo384test.config.Util;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.post.Like;
import com.example.demo384test.model.post.Post;
import com.example.demo384test.repository.LikeRepository;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.PostRepository;
import com.example.demo384test.request.CommentCreationRequest;
import com.example.demo384test.request.HomePostRequest;
import com.example.demo384test.request.LikeCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class LikeController {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;


    @GetMapping(path="/likes/all")
    public @ResponseBody
    Iterable<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    @GetMapping("/process_add_like/{id}")
    public String processAddLike(@PathVariable Long id, HomePostRequest hpr) {
        Post p = postRepository.findByid(id);


        String currentUsername = Util.getCurrentUsername();
        Member m = memberRepository.findByUsername(currentUsername);

        ArrayList<Like> likes = likeRepository.findAllByMemberUsername(currentUsername);

        for(Like like : likes){
            if(like.getPost().getId().equals(p.getId())){
                return "redirect:/";
            }
        }

        Like l = new Like();
        l.setMember(m);
        l.setPost(p);
        likeRepository.save(l);
        hpr.setLike(l);
        return "redirect:/";
    }
}
