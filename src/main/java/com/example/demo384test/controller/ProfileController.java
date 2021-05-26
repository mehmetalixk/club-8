package com.example.demo384test.controller;

import com.example.demo384test.model.Member;
import com.example.demo384test.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/profile/{username}")
    public ModelAndView getProfilePage(@PathVariable String username, Model model){
        Member user = memberRepository.findByUsername(username);

        if(user == null){
            return new ModelAndView("error");
        }

        model.addAttribute("username", username);
        return new ModelAndView("profile");
    }
}
