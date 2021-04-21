package com.example.demo384test.controller;


import com.example.demo384test.model.Club;
import com.example.demo384test.repository.ClubRepository;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.SubclubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path="/clubs")
public class ClubController {
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private SubclubRepository subclubRepository;
    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value="/{title}", method = RequestMethod.GET)
    public ModelAndView getClubPage (@PathVariable String title, Model model) {
        model.addAttribute("club", clubRepository.findByTitle(title));
        return new ModelAndView("club");
    }

    @RequestMapping(value="/{title}/{subclub}", method = RequestMethod.GET)
    public ModelAndView getClubPage (@PathVariable String title, Model model, @PathVariable String subclub) {
        model.addAttribute("club", clubRepository.findByTitle(title));
        model.addAttribute("subclub", subclubRepository.findByTitle(subclub));
        return new ModelAndView("subclub");
        
    }

    @GetMapping(value="/all")
    public @ResponseBody Iterable<Club> getAllClubs() {
        return clubRepository.findAll();
    }
}