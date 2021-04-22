package com.example.demo384test.controller;


import com.example.demo384test.model.Club;
import com.example.demo384test.model.Subclub;
import com.example.demo384test.repository.ClubRepository;
import com.example.demo384test.repository.SubclubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SubclubController {
    @Autowired
    private SubclubRepository subclubRepository;
    @Autowired
    private ClubRepository clubRepository;


    @GetMapping(path="/subclubs/all")
    public @ResponseBody Iterable<Subclub> getAllClubs() {
        return subclubRepository.findAll();
    }

    @PostMapping("/process_add_subclub")
    public ModelAndView processAddSubclub(Subclub subclub) {
        Club c = clubRepository.findByTitle(subclub.getClubTitle());
        c.addSubclubToClub(subclub);
        subclubRepository.save(subclub);
        clubRepository.save(c);
        return new ModelAndView("success");
    }

}