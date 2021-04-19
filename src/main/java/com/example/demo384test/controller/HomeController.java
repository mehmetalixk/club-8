package com.example.demo384test.controller;

import com.example.demo384test.detail.CustomMemberDetails;
import com.example.demo384test.model.*;
import com.example.demo384test.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo384test.handler.PermissionHandler;

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

    private PermissionHandler permissionHandler = new PermissionHandler();

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("home");
    }

    @GetMapping("/register")
    public ModelAndView showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return new ModelAndView("signup_form");
    }

    @GetMapping("/list_members")
    public ModelAndView viewMemberList(Model model) {
        CustomMemberDetails principal = (CustomMemberDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            boolean isAllowed = principal.hasPermission("ROLE_ADMIN");
            if(!isAllowed)
                return null;
        }

        List<Member> listMembers = memberRepository.findAll();
        model.addAttribute("listMembers", listMembers);
        return new ModelAndView("members");
    }

    @GetMapping("/list_roles")
    public ModelAndView viewRoleList(Model model) {
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);
        return new ModelAndView("roles");
    }

    @GetMapping("/list_permissions")
    public ModelAndView viewPermissionList(Model model) {
        List<Permission> listPermissions = permissionRepository.findAll();
        model.addAttribute("listPermissions", listPermissions);
        return new ModelAndView("permissions");
    }

    @GetMapping("/add_permission")
    public ModelAndView showAddPermissionForm(Model model) {
        model.addAttribute("permission", new Permission());
        return new ModelAndView("add_permission");
    }

    @GetMapping("/add_club")
    public ModelAndView showAddClubForm(Model model) {
        model.addAttribute("club", new Club());
        return new ModelAndView("create_club");
    }

    @PostMapping("/process_add_club")
    public ModelAndView processAddClub(Club club) {
        clubRepository.save(club);
        return new ModelAndView("register_success");
    }

    @GetMapping("/add_subclub")
    public ModelAndView showAddSubclubForm(Model model) {
        model.addAttribute("subclub", new Subclub());
        return new ModelAndView("create_subclub");
    }

    @PostMapping("/process_add_subclub")
    public ModelAndView processAddSubclub(Subclub subclub) {
        subclubRepository.save(subclub);
        return new ModelAndView("register_success");
    }

    @GetMapping("/add_role")
    public ModelAndView showAddRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return new ModelAndView();
    }

    @PostMapping("/process_add_role")
    public ModelAndView processAddRole(Role role) {
        roleRepository.save(role);
        return new ModelAndView("register_success");
    }

    @PostMapping("/process_add_permission")
    public ModelAndView processAddPermission(Permission permission) {
        permissionRepository.save(permission);
        return new ModelAndView("register_success");
    }

    @PostMapping("/process_register")
    public ModelAndView processRegistration(Member member) {
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