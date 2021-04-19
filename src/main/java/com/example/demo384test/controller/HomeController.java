package com.example.demo384test.controller;

import com.example.demo384test.detail.CustomMemberDetails;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.Permission;
import com.example.demo384test.model.Role;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.PermissionRepository;
import com.example.demo384test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo384test.handler.PermissionHandler;

import java.util.List;


@RestController
public class HomeController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    private PermissionHandler permissionHandler;

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
        permissionHandler.registerNewUserAccount(member);
        return new ModelAndView("register_success");
    }
}