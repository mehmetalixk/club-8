package com.example.demo384test.controller;

import com.example.demo384test.config.Util;
import com.example.demo384test.model.*;
import com.example.demo384test.model.Club.Club;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Security.Role;
import com.example.demo384test.model.post.Post;
import com.example.demo384test.repository.*;
import com.example.demo384test.request.SubclubCreationRequest;
import com.example.demo384test.service.CustomMemberDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RestController
public class HomeController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private SubclubRepository subclubRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CustomMemberDetailsService customMemberDetailsService;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        List<Post> posts = postRepository.findAll();
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        String username = Util.getCurrentUsername();
        model.addAttribute("subclubs", subclubRepository.findByMembers_username(username));
        model.addAttribute("events", eventRepository.findBySubclub_members_username(username));
        return new ModelAndView("home");
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm(Model model) {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ModelAndView("logout_success");
    }

    @GetMapping("/register")
    public ModelAndView showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return new ModelAndView("signup_form");
    }

    @GetMapping("/admin")
    public ModelAndView adminPanel(Model model) {
        List<Member> listMembers = memberRepository.findAll();
        List<Subclub> listSubclubs = subclubRepository.findAll();
        List<Role> listRoles = roleRepository.findAll();
        List<Club> listClubs = clubRepository.findAll();

        model.addAttribute("listMembers", listMembers);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("listSubclubs", listSubclubs);
        model.addAttribute("listClubs", listClubs);

        model.addAttribute("role", new Role());
        model.addAttribute("club", new Club());
        model.addAttribute("scr", new SubclubCreationRequest());

        model.addAttribute("clubs", clubRepository.findAllTitles());

        return new ModelAndView("admin_panel");
    }

    @PostMapping("/process_register")
    public ModelAndView processRegistration(HttpServletRequest request, Member member, Model model) {
        String username = request.getParameter("username");
        String emailAddress = request.getParameter("emailAddress");
        String message = "";

        try {
            message = customMemberDetailsService.checkDuplicate(username, emailAddress);
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            return new ModelAndView("signup_form");
        }

        model.addAttribute("message", message);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        Role userRole = roleRepository.findByName("ROLE_USER");
        member.setRoles(Arrays.asList(userRole));
        member.setEnabled(true);
        memberRepository.save(member);
        return new ModelAndView("home");
    }
}