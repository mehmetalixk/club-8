package com.example.demo384test.controller;


import com.example.demo384test.config.Util;
import com.example.demo384test.model.Club.Club;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import com.example.demo384test.repository.ClubRepository;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.PostRepository;
import com.example.demo384test.repository.SubclubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClubController {
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private SubclubRepository subclubRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value="/clubs/{title}", method = RequestMethod.GET)
    public ModelAndView getClubPage (@PathVariable String title, Model model) {
        model.addAttribute("club", clubRepository.findByTitle(title));
        model.addAttribute("subclubList", subclubRepository.findAllByClubTitle(title));
        return new ModelAndView("club");
    }

    @RequestMapping(value="/clubs/{title}/{subclub}", method = RequestMethod.GET)
    public ModelAndView getClubPage (@PathVariable String title, Model model, @PathVariable String subclub) {
        model.addAttribute("club", clubRepository.findByTitle(title));
        model.addAttribute("subclub", subclubRepository.findByClubTitle(subclub, title).getTitle());
        model.addAttribute("posts", postRepository.findAllBySubclubTitle(subclub, title));

        String username = Util.getCurrentUsername();
        Member currentUser = memberRepository.findByUsername(username);
        Subclub currentSubclub = subclubRepository.findByClubTitle(subclub, title);
        boolean isMember = currentSubclub.getMembers().contains(currentUser);
        if(isMember){
            return new ModelAndView("subclub");
        }else{
            return new ModelAndView("error");
        }
    }

    @GetMapping(value="/clubs/all")
    public @ResponseBody Iterable<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @PostMapping("/process_add_club")
    public ModelAndView processAddClub(Club club) {
        clubRepository.save(club);
        return new ModelAndView("success");
    }

}