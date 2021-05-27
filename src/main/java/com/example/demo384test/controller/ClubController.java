package com.example.demo384test.controller;

import com.example.demo384test.config.Util;
import com.example.demo384test.model.Club.Club;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.Security.Permission;
import com.example.demo384test.model.Security.Role;
import com.example.demo384test.model.post.Event;
import com.example.demo384test.repository.*;
import com.example.demo384test.request.ClubEditionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private EventRepository eventRepository;

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
        List<Event> events = eventRepository.findAllBySubclubTitle(subclub, title);
        List<Event> lastThreeEvents = events.subList(Math.max(events.size() - 3, 0), events.size());
        List<Member> members = subclubRepository.findByClubTitle(subclub, title).getMembers().
                stream().collect(Collectors.toList());
        List<Member> lastThreeMembers = members;
        if(Math.max(members.size() - 3, 0) > members.size()){
            lastThreeMembers = members.subList(Math.max(members.size() - 3, 0), members.size());}
        model.addAttribute("members", lastThreeMembers);
        model.addAttribute("events", lastThreeEvents);


        // Check permission to read this subclub
        String username = Util.getCurrentUsername();
        Member currentUser = memberRepository.findByUsername(username);
        if(Util.isAdmin(currentUser))
            return new ModelAndView("subclub");
        if(Util.checkReadPermission(currentUser,title, subclub))
            return new ModelAndView("subclub");
        return new ModelAndView("error");
    }

    @GetMapping(value="/clubs/all")
    public @ResponseBody Iterable<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @PostMapping("/process_add_club")
    public String processAddClub(Club club) {
        clubRepository.save(club);
        return "redirect:/admin";
    }

    @RequestMapping(value="/process_remove_club/{clubID}", method = RequestMethod.GET)
    public String processRemoveClub(@PathVariable String clubID) {
        Long id = Long.parseLong(clubID);
        Club c = clubRepository.findByID(id);
        Collection<Subclub> scs= subclubRepository.findAllByClubTitle(c.getTitle());
        /*Delete All Posts that Belong to the Sub-clubs of the Club*/
        for(Subclub sc : scs)
            postRepository.deleteAll(postRepository.findAllBySubclubTitle(sc.getTitle(), c.getTitle()));
        /*Delete All Sub-clubs that Belong to the Club*/
        subclubRepository.deleteAll(scs);
        /*Delete the club*/
        clubRepository.delete(c);
        return "redirect:/admin";
    }

    @RequestMapping(value="/clubs/{clubID}/edit", method = RequestMethod.GET)
    public ModelAndView getEditClub(@PathVariable String clubID, Model model) {
        Long id = Long.parseLong(clubID);
        Club club = clubRepository.findByID(id);

        if(club == null) {
            return new ModelAndView("error");
        }

        ClubEditionRequest cer = new ClubEditionRequest();
        cer.setId(clubID);
        model.addAttribute("club", club);
        model.addAttribute("cer", cer);
        return new ModelAndView("edit_club");
    }

    @PostMapping("/process_edit_club")
    public String processEditClub(ClubEditionRequest cer) {
        Club club = clubRepository.findByID(Long.parseLong(cer.getId()));

        /* If same club name already exists throw an error*/
        if(clubRepository.findByTitle(cer.getTitle()) != null)
            return "redirect:/error";

        club.setTitle(cer.getTitle());

        clubRepository.save(club);

        return "redirect:/admin";
    }

    @GetMapping("/viewAllSubclubs")
    public ModelAndView viewAllSubclubs(Model model){
        String currentUsername = Util.getCurrentUsername();
        Member m = memberRepository.findByUsername(currentUsername);

        List<Subclub> subclubs = subclubRepository.findByMembers_username(currentUsername);
        List<Subclub> allSubclubs = subclubRepository.findAll();
        model.addAttribute("subclubs", subclubs);
        model.addAttribute("allSubclubs", allSubclubs);
        return new ModelAndView("show_allSubclubs");
    }
}