package com.example.demo384test.controller;


import com.example.demo384test.model.Club.Club;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.repository.ClubRepository;
import com.example.demo384test.repository.PostRepository;
import com.example.demo384test.repository.SubclubRepository;
import com.example.demo384test.request.SubclubCreationRequest;
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
    @Autowired
    private PostRepository postRepository;


    @GetMapping(path="/subclubs/all")
    public @ResponseBody Iterable<Subclub> getAllClubs() {
        return subclubRepository.findAll();
    }

    @PostMapping("/process_add_subclub")
    public String processAddSubclub(SubclubCreationRequest scr) {
        Club c = clubRepository.findByTitle(scr.getClubTitle());
        Subclub sc = new Subclub();
        sc.setTitle(scr.getTitle());
        sc.setClub(c);
        subclubRepository.save(sc);
        clubRepository.save(c);
        return "redirect:/admin";
    }

    @RequestMapping(value="/process_remove_subclub/{subclubID}", method = RequestMethod.GET)
    public String processRemoveSubclub(@PathVariable String subclubID) {
        Long id = Long.parseLong(subclubID);
        Subclub sc = subclubRepository.findByID(id);
        postRepository.deleteAll(postRepository.findAllBySubclubTitle(sc.getTitle(), sc.getClub().getTitle()));
        System.out.println("All posts are removed from the post repository which are belong to " + sc.getTitle());
        subclubRepository.delete(sc);
        System.out.println(sc.getTitle() + " " + "removed from the repository");
        return "redirect:/admin";
    }

}