package com.example.demo384test.controller;


import com.example.demo384test.model.Role;
import com.example.demo384test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/roles")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewRole (@RequestParam String name) {
        Role n = new Role();
        n.setName(name);
        roleRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}