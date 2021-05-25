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


    @GetMapping("/create/event")
    public ModelAndView createEvent(Model model) {
        model.addAttribute("ecr", new EventCreationRequest());
        model.addAttribute("subclubList", subclubRepository.findAllTitles());
        model.addAttribute("clubList", clubRepository.findAllTitles());
        return new ModelAndView("create_event");
    }

    @PostMapping("/create/event/process")
    public String processCreateEvent(EventCreationRequest ecr) {
        Event e = new Event();

        e.setMember(memberRepository.findByUsername(Util.getCurrentUsername()));
        e.setTime(ecr.getTime());
        e.setDate(ecr.getDate());
        e.setLocation(ecr.getLocation());
        e.setContent(ecr.getContent());
        e.setSubclub(subclubRepository.findByClubTitle(ecr.getSubclubTitle(), ecr.getClubTitle()));

        eventRepository.save(e);
        return "redirect:/";
    }

}
