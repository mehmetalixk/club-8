package com.example.demo384test.controller;


import com.example.demo384test.model.Subclub;
import com.example.demo384test.repository.SubclubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/subclubs")
public class SubclubController {
    @Autowired
    private SubclubRepository subclubRepository;


    @GetMapping(path="/all")
    public @ResponseBody Iterable<Subclub> getAllClubs() {
        return subclubRepository.findAll();
    }
}