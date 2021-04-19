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

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewPermission (@RequestParam String name) {
        Subclub n = new Subclub();
        n.setTitle(name);
        subclubRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/")
    public @ResponseBody Iterable<Subclub> getAllClubs() {
        return subclubRepository.findAll();
    }
}