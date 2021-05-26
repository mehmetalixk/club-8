package com.example.demo384test.controller;

import com.example.demo384test.Logger.LogRepository;
import com.example.demo384test.Logger.Logger;
import com.example.demo384test.config.Util;
import com.example.demo384test.model.*;
import com.example.demo384test.model.Club.Club;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Security.Permission;
import com.example.demo384test.model.Security.Role;
import com.example.demo384test.model.post.Event;
import com.example.demo384test.model.post.Poll;
import com.example.demo384test.model.post.Post;
import com.example.demo384test.model.post.Question;
import com.example.demo384test.repository.*;
import com.example.demo384test.request.HomePostRequest;
import com.example.demo384test.request.SubclubCreationRequest;
import com.example.demo384test.request.SubclubEditionRequest;
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
import java.util.*;


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
    private EventRepository eventRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CustomMemberDetailsService customMemberDetailsService;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        String username = Util.getCurrentUsername();
        Collection<Subclub> subclubs = subclubRepository.findByMembers_username(username);

        List<Post> posts = postRepository.findAll();
        List<Post> posts_user = new ArrayList<>();
        for(Post p : posts) {
            if(subclubs.contains(p.getSubclub()))
                posts_user.add(p);
        }
        Collections.sort(posts_user, Comparator.comparing(Post::getDate).thenComparing(Post::getTimestamp).reversed());

        Collections.reverse(posts);

        List<HomePostRequest> hprs = new ArrayList<>();

        for(Post p : posts_user) {
            HomePostRequest hpr = new HomePostRequest();
            hpr.setPost(p);
            hpr.setComments(commentRepository.findByPostID(p.getId()).size());
            hpr.setLikes(likeRepository.findAllByPostTitle(p.getTitle()).size());
            hprs.add(hpr);
        }


        model.addAttribute("hprs", hprs);
        model.addAttribute("subclubs", subclubs);
        List<Event> events = eventRepository.findBySubclub_members_username(username);
        List<Event> lastThreeEvents = events.subList(Math.max(events.size() - 3, 0), events.size());
        model.addAttribute("events", lastThreeEvents);
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
        return new ModelAndView("register");
    }

    @GetMapping("/admin")
    public ModelAndView adminPanel(Model model) {
        List<Member> listMembers = memberRepository.findAll();
        List<Subclub> listSubclubs = subclubRepository.findAll();
        List<Role> listRoles = roleRepository.findAll();
        List<Club> listClubs = clubRepository.findAll();
        List<Poll> listPolls = pollRepository.findAll();
        List<Question> listQuestions = questionRepository.findAll();

        List<Logger> warnings = logRepository.findByLevel("WARN");
        Collections.reverse(warnings);
        List<Logger> infos = logRepository.findByLevel("INFO");
        Collections.reverse(infos);
        List<Logger> errors = logRepository.findByLevel("ERR");
        Collections.reverse(errors);
        List<Logger> requests = logRepository.findByLevel("REQ");
        Collections.reverse(requests);
        List<Logger> reports = logRepository.findByLevel("REP");
        Collections.reverse(reports);


        model.addAttribute("listMembers", listMembers);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("listSubclubs", listSubclubs);
        model.addAttribute("listClubs", listClubs);
        model.addAttribute("listPolls", listPolls);
        model.addAttribute("listQuestions", listQuestions);

        model.addAttribute("listWarnings", warnings);
        model.addAttribute("listInfos", infos);
        model.addAttribute("listErrors", errors);
        model.addAttribute("requests", requests);
        model.addAttribute("reports", reports);

        model.addAttribute("role", new Role());
        model.addAttribute("club", new Club());
        model.addAttribute("poll", new Poll());
        model.addAttribute("question", new Question());

        model.addAttribute("scr", new SubclubCreationRequest());
        model.addAttribute("scer", new SubclubEditionRequest());

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
            return new ModelAndView("register");
        }

        model.addAttribute("message", message);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        member.setBanned(false);
        Role userRole = roleRepository.findByName("ROLE_USER");
        member.setRoles(Arrays.asList(userRole));
        member.setEnabled(true);
        memberRepository.save(member);



        // get initial poll
        Poll initialPoll = pollRepository.findByID(1L);
        model.addAttribute("poll", initialPoll);
        model.addAttribute("questions", initialPoll.getQuestions());
        // redirect to poll
        return new ModelAndView("poll");
    }
}