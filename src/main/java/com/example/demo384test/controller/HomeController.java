package com.example.demo384test.controller;

import com.example.demo384test.model.Member;
import com.example.demo384test.model.Permission;
import com.example.demo384test.model.Role;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.PermissionRepository;
import com.example.demo384test.repository.RoleRepository;
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
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private CustomMemberDetailsService memberService;

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("home");
    }

    @GetMapping("/register")
    public ModelAndView showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return new ModelAndView("signup_form");
    }

    @PostMapping("/process_register")
    public ModelAndView processRegistration(Member member) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);
        return new ModelAndView("register_success");
    }

    @GetMapping("/list_members")
    public ModelAndView viewMemberList(Model model){
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

    @PostMapping("/process_add_permission")
    public ModelAndView processAddPermission(Permission permission) {
        permissionRepository.save(permission);
        return new ModelAndView("register_success");
    }
}