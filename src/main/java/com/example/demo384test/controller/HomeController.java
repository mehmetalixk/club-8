package com.example.demo384test.controller;

import com.example.demo384test.model.Member;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.service.CustomMemberDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
public class HomeController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CustomMemberDetailsService memberService;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup_form");
        return modelAndView;
    }

    @PostMapping("/process_register")
    public ModelAndView processRegistration(Member member) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register_success");
        return modelAndView;
    }

    @GetMapping("/list_members")
    public ModelAndView viewMemberList(Model model){
        List<Member> listMembers = memberRepository.findAll();
        model.addAttribute("listMembers", listMembers);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("members");
        return modelAndView;
    }
}