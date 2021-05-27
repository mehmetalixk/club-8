package com.example.demo384test.controller;


import com.example.demo384test.model.Security.Role;
import com.example.demo384test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path="/roles/add")
    public @ResponseBody
    String addNewRole (@RequestParam String name) {
        Role n = new Role();
        n.setName(name);
        roleRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/roles/all")
    public @ResponseBody Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @PostMapping("/process_add_role")
    public String processAddRole(Role role) {
        roleRepository.save(role);
        return ("redirect:/admin");
    }
}