package com.example.demo384test.controller;


import com.example.demo384test.model.Club;
import com.example.demo384test.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/clubs")
public class ClubController {
    @Autowired
    private ClubRepository clubRepository;

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewPermission (@RequestParam String name) {
        Club n = new Club();
        n.setTitle(name);
        clubRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/")
    public @ResponseBody Iterable<Club> getAllClubs() {
        return clubRepository.findAll();
    }
}