package com.example.demo384test.controller;

import com.example.demo384test.model.post.Poll;
import com.example.demo384test.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PollController {
    @Autowired
    private PollRepository pollRepository;


    @GetMapping(value="/polls/all")
    public @ResponseBody
    Iterable<Poll> getAllClubs() {
        return pollRepository.findAll();
    }

    @PostMapping("/polls/add/process")
    public String processAddPoll(Poll poll) {
        pollRepository.save(poll);
        return "redirect:/admin";
    }

}
