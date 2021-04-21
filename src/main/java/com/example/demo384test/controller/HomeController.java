package com.example.demo384test.controller;

import com.example.demo384test.detail.CustomMemberDetails;
import com.example.demo384test.model.*;
import com.example.demo384test.repository.*;
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
import java.util.List;


@RestController
public class HomeController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private SubclubRepository subclubRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CustomMemberDetailsService customMemberDetailsService;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        return new ModelAndView("home");
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm (Model model) {
        return new ModelAndView("login");
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ModelAndView("logout_success");
    }

    @GetMapping("/register")
    public ModelAndView showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return new ModelAndView("signup_form");
    }

    @PostMapping("/process_add_club")
    public ModelAndView processAddClub(Club club) {
        clubRepository.save(club);
        return new ModelAndView("success");
    }

    @PostMapping("/process_add_subclub")
    public ModelAndView processAddSubclub(Subclub subclub) {
        Club c = clubRepository.findByTitle(subclub.getClubTitle());
        c.addSubclubToClub(subclub);
        subclubRepository.save(subclub);
        clubRepository.save(c);
        return new ModelAndView("success");
    }

    @GetMapping("/post")
    public ModelAndView post(Model model) {
        CustomMemberDetails principal = (CustomMemberDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            boolean isAllowed = principal.hasPermission("ROLE_ADMIN") || principal.hasPermission("ROLE_USER");
            if(!isAllowed)
                return null;
        }

        model.addAttribute("post", new Post());
        model.addAttribute("subclubList", subclubRepository.findAllTitles());
        return new ModelAndView("post");
    }

    @PostMapping("/process_add_post")
    public ModelAndView processAddPost(Post post) {
        // READ subclub from the subclub repository
        Subclub sc = subclubRepository.findByTitle(post.getSubclubTitle());
        post.setDate(java.time.LocalDate.now());
        post.setTimestamp(java.time.LocalTime.now());
        // Get logged member
        CustomMemberDetails principal = (CustomMemberDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member m = memberRepository.findByUsername(principal.getUsername());
        post.setMemberUsername(m.getUsername());
        m.addPost(post);
        sc.addPostToSubclub(post);

        // CREATE a new post in post repository
        postRepository.save(post);
        // UPDATE the subclub with new post
        subclubRepository.save(sc);
        // UPDATE the member with new post
        memberRepository.save(m);
        return new ModelAndView("success");

    }

    @PostMapping("/process_add_role")
    public ModelAndView processAddRole(Role role) {
        roleRepository.save(role);
        return new ModelAndView("success");
    }

    @PostMapping("/process_add_permission")
    public ModelAndView processAddPermission(Permission permission) {
        permissionRepository.save(permission);
        return new ModelAndView("success");
    }

    @GetMapping("/admin")
    public ModelAndView adminPanel(Model model){
        CustomMemberDetails principal =
                (CustomMemberDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            boolean isAllowed = principal.hasPermission("ROLE_ADMIN");
            if(!isAllowed)
                return null;
        }

        List<Member> listMembers = memberRepository.findAll();
        model.addAttribute("listMembers", listMembers);

        model.addAttribute("role", new Role());

        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);

        model.addAttribute("club", new Club());

        model.addAttribute("subclub", new Subclub());

        model.addAttribute("clubs", clubRepository.findAllTitles());

        List<Club> listClubs = clubRepository.findAll();
        model.addAttribute("listClubs", listClubs);

        List<Subclub> listSubclubs = subclubRepository.findAll();
        model.addAttribute("listSubclubs", listSubclubs);

        return new ModelAndView("admin_panel");
    }

    @PostMapping("/admin_add_role")
    public ModelAndView adminAddRole(Role role) {
        roleRepository.save(role);
        return new ModelAndView("admin_panel");
    }

    @PostMapping("/process_register")
    public ModelAndView processRegistration(HttpServletRequest request, Member member, Model model) {
        String username = request.getParameter("username");
        String emailAddress = request.getParameter("emailAddress");
        String message = "";

        try{
            message = customMemberDetailsService.checkDuplicate(username, emailAddress);
        }catch(UsernameNotFoundException ex){
            model.addAttribute("error", ex.getMessage());
            return new ModelAndView("signup_form");
        }

        model.addAttribute("message", message);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        System.out.println(roleRepository.findAll());

        Role memberRole = roleRepository.findByName("ROLE_USER");
        member.setRoles(Arrays.asList(memberRole));
        memberRepository.save(member);
        return new ModelAndView("register_success");
    }
}