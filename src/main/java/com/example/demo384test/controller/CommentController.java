package com.example.demo384test.controller;

import com.example.demo384test.model.Club.Comment;
import com.example.demo384test.repository.CommentRepository;
import com.example.demo384test.request.CommentCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;


    @GetMapping(path="/comments/all")
    public @ResponseBody Iterable<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @PostMapping("/process_add_comment")
    public ModelAndView processAddComment(CommentCreationRequest ccr) {
        /*
        * Create comments here
        * */
        return new ModelAndView("success");
    }
}
