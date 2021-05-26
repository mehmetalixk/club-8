package com.example.demo384test.controller;

import com.example.demo384test.model.post.Question;
import com.example.demo384test.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions/add/process")
    public String processAddQuestion(Question question) {
        questionRepository.save(question);
        return "redirect:/admin";
    }

}
