package com.example.demo384test.controller;


import com.example.demo384test.model.Member;
import com.example.demo384test.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/demo")
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;
    @PostMapping(path="/add")
    public @ResponseBody
    String addNewUser (@RequestParam String name,
                       @RequestParam String surname,
                       @RequestParam String username,
                       @RequestParam String password,
                       @RequestParam String emailAddress,
                       @RequestParam String gender) {
        Member n = new Member();
        n.setName(name);
        n.setSurname(surname);
        n.setUsername(username);
        n.setPassword(password);
        n.setEmailAddress(emailAddress);
        n.setGender(gender);
        memberRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Member> getAllUsers() {
        return memberRepository.findAll();
    }
}
