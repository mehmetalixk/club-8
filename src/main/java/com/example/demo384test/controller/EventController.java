package com.example.demo384test.controller;

import com.example.demo384test.config.Util;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.post.Event;
import com.example.demo384test.repository.ClubRepository;
import com.example.demo384test.repository.EventRepository;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.SubclubRepository;
import com.example.demo384test.request.EventCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class EventController {

    @Autowired
    private SubclubRepository subclubRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value="/events/{eventID}", method = RequestMethod.GET)
    public ModelAndView getClubPage (@PathVariable String eventID, Model model) {
        Long id = Long.parseLong(eventID);

        Event event = eventRepository.findByID(id);

        if(event == null)
            return new ModelAndView("error");

        model.addAttribute("event", event);
        return new ModelAndView("show_event");
    }


    @GetMapping("/event/{title}/{subclub}")
    public ModelAndView createEvent(@PathVariable String title, @PathVariable String subclub, Model model) {
        model.addAttribute("club", title);
        model.addAttribute("subclub", subclub);
        model.addAttribute("ecr", new EventCreationRequest());
        return new ModelAndView("create_event");
    }

    @PostMapping("/process_add_event")
    public String processCreateEvent(EventCreationRequest ecr) {
        Event e = new Event();

        String currentUsername = Util.getCurrentUsername();
        Member m = memberRepository.findByUsername(currentUsername);

        // Get subclub
        Subclub sc = subclubRepository.findByClubTitle(ecr.getSubclubTitle(), ecr.getClubTitle());

        boolean isAdmin = Util.isAdmin(m);
        boolean isMember = Util.checkWritePermission(m, ecr.getClubTitle(), ecr.getSubclubTitle());

        if(isMember || isAdmin){
            e.setMember(m);
            e.setTime(LocalTime.parse(ecr.getDateTime().split("T")[1]));
            e.setDate(LocalDate.parse(ecr.getDateTime().split("T")[0]));
            e.setLocation(ecr.getLocation());
            e.setContent(ecr.getContent());
            e.setSubclub(sc);
            eventRepository.save(e);
            return "redirect:/clubs/" + ecr.getClubTitle() + "/" + ecr.getSubclubTitle();
        }else{
            return"redirect:/error";
        }
    }

}
