package com.example.demo384test.controller;

import com.example.demo384test.Logger.LogController;
import com.example.demo384test.config.Util;
import com.example.demo384test.model.post.Question;
import com.example.demo384test.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions/add/process")
    public String processAddQuestion(Question question) {
        questionRepository.save(question);
        return "redirect:/admin";
    }

    @RequestMapping(path="/questions/delete/{questionID}")
    public String deleteQuestion(@PathVariable String questionID) {

        Long id = Long.parseLong(questionID);
        Question question = questionRepository.findByID(id);

        if(question == null) {
            LogController.createLog("WARN",  String.format("Question with id %s not found on the repository!", questionID));
            return "redirect:/admin";
        }

        questionRepository.delete(question);
        LogController.createLog("INFO",  String.format("Question with id has been deleted by %s", questionID, Util.getCurrentUsername()));

        return "redirect:/admin";
    }

}
