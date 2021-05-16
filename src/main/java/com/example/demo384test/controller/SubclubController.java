package com.example.demo384test.controller;


import com.example.demo384test.model.Club.Club;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.repository.ClubRepository;
import com.example.demo384test.repository.PostRepository;
import com.example.demo384test.repository.SubclubRepository;
import com.example.demo384test.request.ClubEditionRequest;
import com.example.demo384test.request.SubclubCreationRequest;
import com.example.demo384test.request.SubclubEditionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value="/clubs/{clubTitle}/{subclubID}/edit", method = RequestMethod.GET)
    public ModelAndView getEditSubclub(@PathVariable String clubTitle, @PathVariable String subclubID, Model model) {
        Long id = Long.parseLong(subclubID);
        Club club = clubRepository.findByTitle(clubTitle);
        /*Check if the club is exists*/
        if(club == null) {
            return new ModelAndView("error");
        }

        Subclub sc = subclubRepository.findByID(id);
        /*Check if the sub-club is exists*/
        if(sc == null) {
            return new ModelAndView("error");
        }
        SubclubEditionRequest scer = new SubclubEditionRequest();
        scer.setId(subclubID);
        model.addAttribute("subclub", sc);
        model.addAttribute("scer", scer);
        return new ModelAndView("edit_subclub");
    }

    @PostMapping("/process_edit_subclub")
    public String processEditSubclub(SubclubEditionRequest scer) {
        Subclub sc = subclubRepository.findByID(Long.parseLong(scer.getId()));

        /* If same subclub name already exists throw an error*/
        if(subclubRepository.findByClubTitle(scer.getTitle(), sc.getClub().getTitle()) != null)
            return "redirect:/error";

        sc.setTitle(scer.getTitle());

        subclubRepository.save(sc);

        return "redirect:/admin";
    }

}